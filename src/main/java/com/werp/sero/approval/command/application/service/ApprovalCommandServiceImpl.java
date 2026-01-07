package com.werp.sero.approval.command.application.service;

import com.werp.sero.approval.command.application.dto.*;
import com.werp.sero.approval.command.domain.aggregate.Approval;
import com.werp.sero.approval.command.domain.aggregate.ApprovalAttachment;
import com.werp.sero.approval.command.domain.aggregate.ApprovalLine;
import com.werp.sero.approval.command.domain.aggregate.enums.ApprovalNotificationType;
import com.werp.sero.approval.command.domain.repository.ApprovalAttachmentRepository;
import com.werp.sero.approval.command.domain.repository.ApprovalLineRepository;
import com.werp.sero.approval.command.domain.repository.ApprovalRepository;
import com.werp.sero.approval.exception.*;
import com.werp.sero.common.file.S3Uploader;
import com.werp.sero.common.util.DateTimeUtils;
import com.werp.sero.employee.command.domain.aggregate.Employee;
import com.werp.sero.employee.command.domain.repository.EmployeeRepository;
import com.werp.sero.employee.exception.EmployeeNotFoundException;
import com.werp.sero.notification.command.domain.aggregate.enums.NotificationType;
import com.werp.sero.notification.command.infrastructure.event.NotificationEvent;
import com.werp.sero.order.command.domain.aggregate.SalesOrder;
import com.werp.sero.order.command.domain.repository.SORepository;
import com.werp.sero.production.command.domain.aggregate.ProductionRequest;
import com.werp.sero.shipping.command.domain.aggregate.GoodsIssue;
import com.werp.sero.system.command.application.service.DocumentSequenceCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ApprovalCommandServiceImpl implements ApprovalCommandService {
    private static final String APPROVAL_DOC_TYPE_CODE = "DOC_SERO";
    private static final String APPROVAL_TYPE_APPROVAL = "AT_APPR";
    private static final String APPROVAL_TYPE_REVIEWER = "AT_RVW";
    private static final String APPROVAL_TYPE_REFERENCE = "AT_REF";
    private static final String APPROVAL_TYPE_RECIPIENT = "AT_RCPT";

    private final EmployeeRepository employeeRepository;
    private final ApprovalRepository approvalRepository;
    private final ApprovalLineRepository approvalLineRepository;
    private final ApprovalAttachmentRepository approvalAttachmentRepository;
    private final List<ApprovalRefCodeValidator> approvalRefCodeValidators;
    private final SORepository soRepository;

    private final S3Uploader s3Uploader;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final DocumentSequenceCommandService documentSequenceCommandService;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    @Override
    public ApprovalResponseDTO submitForApproval(Employee employee, final ApprovalCreateRequestDTO requestDTO,
                                                 final List<MultipartFile> files) {
        validateDuplicateApproval(requestDTO.getRefCode());

        validateApprovalLines(requestDTO.getApprovalLines());

        final Object ref = validateRefCode(requestDTO.getApprovalTargetType(), requestDTO.getRefCode());

        final String approvalCode = documentSequenceCommandService.generateDocumentCode(APPROVAL_DOC_TYPE_CODE);

        final Approval approval = saveApproval(employee, approvalCode, requestDTO);

        List<ApprovalAttachmentResponseDTO> approvalAttachmentResponseDTOs = new ArrayList<>();

        if (files != null && !files.isEmpty()) {
            approvalAttachmentResponseDTOs = saveApprovalAttachments(approval, files).stream()
                    .map(ApprovalAttachmentResponseDTO::of)
                    .collect(Collectors.toList());
        }

        final List<ApprovalLine> approvalLines = saveApprovalLines(approval, requestDTO.getApprovalLines());

        final List<ApprovalLineResponseDTO> approvalLineResponseDTOs = approvalLines.stream()
                .filter(approvalLine ->
                        approvalLine.getLineType().equals(APPROVAL_TYPE_APPROVAL) ||
                                approvalLine.getLineType().equals(APPROVAL_TYPE_REVIEWER))
                .sorted(Comparator.comparingInt(ApprovalLine::getSequence))
                .map(ApprovalLineResponseDTO::of)
                .collect(Collectors.toList());

        final List<ApprovalLineResponseDTO> refLines = approvalLines.stream()
                .filter(approvalLine -> approvalLine.getLineType().equals(APPROVAL_TYPE_REFERENCE))
                .map(ApprovalLineResponseDTO::of)
                .collect(Collectors.toList());

        final List<ApprovalLineResponseDTO> rcptLines = approvalLines.stream()
                .filter(approvalLine -> approvalLine.getLineType().equals(APPROVAL_TYPE_RECIPIENT))
                .map(ApprovalLineResponseDTO::of)
                .collect(Collectors.toList());

        updateRefCode(requestDTO.getApprovalTargetType(), approvalCode, ref);
        sendApprovalNotification(approval, ApprovalNotificationType.REQUEST,
                approvalLineResponseDTOs.get(0).getApproverId());

        return ApprovalResponseDTO.of(approval, approvalAttachmentResponseDTOs, approvalLineResponseDTOs, refLines, rcptLines);
    }

    @Transactional
    @Override
    public void approve(final Employee employee, final int approvalId, final ApprovalDecisionRequestDTO requestDTO) {
        final Approval approval = findApprovalById(approvalId);

        final ApprovalLine approvalLine = findApprovalLineByApprovalAndEmployee(approval, employee);

        validateApprovable(approval, approvalLine);

        final String documentPrefix = approval.getRefCode().substring(0, 2);

        final Object ref = validateRefCode(documentPrefix, approval.getRefCode());

        final String now = DateTimeUtils.nowDateTime();

        approvalLine.updateApprovalLine("ALS_APPR", requestDTO.getNote(), now);

        if (hasNextApprover(approval, approvalLine)) {
            activateNextApprover(approval, approvalLine.getSequence());

            return;
        }

        updateRefDocumentStatus("AS_APPR", documentPrefix, ref);

        approval.updateApprovalStatus("AS_APPR", now);

        sendApprovalNotification(approval, ApprovalNotificationType.APPROVED, approval.getEmployee().getId());
    }

    @Transactional
    @Override
    public void reject(final Employee employee, final int approvalId, final ApprovalDecisionRequestDTO requestDTO) {
        final Approval approval = findApprovalById(approvalId);

        final ApprovalLine approvalLine = findApprovalLineByApprovalAndEmployee(approval, employee);

        validateApprovable(approval, approvalLine);

        final String documentPrefix = approval.getRefCode().substring(0, 2);

        final Object ref = validateRefCode(documentPrefix, approval.getRefCode());

        updateRefDocumentStatus("AS_RJCT", documentPrefix, ref);

        final String now = DateTimeUtils.nowDateTime();

        approvalLine.updateApprovalLine("ALS_RJCT", requestDTO.getNote(), now);

        approval.updateApprovalStatus("AS_RJCT", now);

        sendApprovalNotification(approval, ApprovalNotificationType.REJECTED, approval.getEmployee().getId());
    }

    private void validateApprovable(final Approval approval, final ApprovalLine approvalLine) {
        if (!"ALS_RVW".equals(approvalLine.getStatus())) {
            throw new ApprovalNotCurrentSequenceException();
        }

        if (!"AS_ING".equals(approval.getStatus())) {
            throw new ApprovalAlreadyProcessedException();
        }
    }

    private boolean hasNextApprover(final Approval approval, final ApprovalLine approvalLine) {
        return approvalLineRepository.existsByApprovalAndSequenceIsNotNullAndSequenceGreaterThan(approval, approvalLine.getSequence());
    }

    private void activateNextApprover(final Approval approval, final int approvalLineSequence) {
        final ApprovalLine approvalLine =
                approvalLineRepository.findFirstByApprovalAndSequenceGreaterThanOrderBySequenceAsc(approval, approvalLineSequence)
                        .orElseThrow();

        approvalLine.updateStatus("ALS_RVW");

        sendApprovalNotification(approval, ApprovalNotificationType.REQUEST, approvalLine.getEmployee().getId());
    }

    private void updateRefDocumentStatus(final String approvalStatus, String documentPrefix, final Object object) {
        final boolean isRejected = approvalStatus.equals("AS_RJCT");

        switch (documentPrefix) {
            case "SO" -> {
                final SalesOrder so = (SalesOrder) object;

                if (so.getApprovalCode() == null) {
                    throw new ApprovalNotSubmittedException();
                }

                if (!"ORD_APPR_PEND".equals(so.getStatus())) {
                    throw new ApprovalRefDocumentAlreadyProcessedException();
                }

                so.updateApprovalInfo(so.getApprovalCode(), (isRejected ? "ORD_APPR_RJCT" : "ORD_APPR_DONE"));

                if(!isRejected){
                    eventPublisher.publishEvent(NotificationEvent.forClient(
                            NotificationType.ORDER,
                            "주문 상태 변경",
                            "주문번호 " + so.getSoCode() + "의 상태가 진행중으로 변경되었습니다.",
                            so.getClientEmployee().getId(),
                            "client-portal/order/management" + so.getId()
                    ));
                }
            }
            case "GI" -> {
                final GoodsIssue gi = (GoodsIssue) object;

                if (gi.getApprovalCode() == null) {
                    throw new ApprovalNotSubmittedException();
                }

                if (!"GI_APPR_PEND".equals(gi.getStatus())) {
                    throw new ApprovalRefDocumentAlreadyProcessedException();
                }

                gi.updateApprovalInfo(gi.getApprovalCode(), (isRejected ? "GI_APPR_RJCT" : "GI_APPR_DONE"));

                // 출고지시 결재 승인 시 연관된 주문 상태도 업데이트
                if (!isRejected) {
                    SalesOrder salesOrder = gi.getSalesOrder();
                    // 주문이 결재 완료 상태가 아니라면 출고 진행 중 상태로 변경
                    if ("ORD_APPR_DONE".equals(salesOrder.getStatus())) {
                        salesOrder.updateApprovalInfo(salesOrder.getApprovalCode(), "ORD_SHIP_READY");
                        soRepository.save(salesOrder);
                    }
                }
            }
            case "PR" -> {
                final ProductionRequest pr = (ProductionRequest) object;

                if (pr.getApprovalCode() == null) {
                    throw new ApprovalNotSubmittedException();
                }

                if (!"PR_APPR_PEND".equals(pr.getStatus())) {
                    throw new ApprovalRefDocumentAlreadyProcessedException();
                }

                pr.updateApprovalInfo(pr.getApprovalCode(), (isRejected ? "PR_APPR_RJCT" : "PR_APPR_DONE"));
            }
            default -> throw new InvalidDocumentTypeException();
        }
    }

    private void sendApprovalNotification(final Approval approval, final ApprovalNotificationType type,
                                          final int approverId) {
        applicationEventPublisher.publishEvent(new NotificationEvent(
                NotificationType.APPROVAL,
                type.getTitle(approval),
                type.getContent(approval),
                approverId,
                "/approval/" + approval.getId()
        ));
    }

    private ApprovalLine findApprovalLineByApprovalAndEmployee(final Approval approval, final Employee employee) {
        return approvalLineRepository.findByApprovalAndEmployee(approval, employee)
                .orElseThrow(ApprovalLineAccessDeniedException::new);
    }

    private Approval findApprovalById(final int approvalId) {
        return approvalRepository.findById(approvalId)
                .orElseThrow(ApprovalNotFoundException::new);
    }

    private void validateDuplicateApproval(final String refCode) {
        if (approvalRepository.existsByRefCode(refCode)) {
            throw new ApprovalDuplicatedException();
        }
    }

    private void updateRefCode(final String approvalTargetType, final String approvalCode,
                               final Object object) {
        switch (approvalTargetType) {
            case "SO" -> ((SalesOrder) object).updateApprovalInfo(approvalCode, "ORD_APPR_PEND");
            case "GI" -> ((GoodsIssue) object).updateApprovalInfo(approvalCode, "GI_APPR_PEND");
            case "PR" -> ((ProductionRequest) object).updateApprovalInfo(approvalCode, "PR_APPR_PEND");
            default -> throw new InvalidDocumentTypeException();
        }
    }

    private Object validateRefCode(final String approvalTargetType, final String refCode) {
        return approvalRefCodeValidators.stream()
                .filter(validator -> validator.supports(approvalTargetType))
                .findFirst()
                .orElseThrow(InvalidDocumentTypeException::new)
                .validate(refCode);
    }

    private Approval saveApproval(final Employee employee, final String approvalCode,
                                  final ApprovalCreateRequestDTO requestDTO) {
        final int totalLine = calculateTotalApprovalLineCount(requestDTO.getApprovalLines());

        final Approval approval = new Approval(approvalCode, requestDTO.getTitle(), requestDTO.getContent(),
                totalLine, requestDTO.getRefCode(), DateTimeUtils.nowDateTime(), employee);

        return approvalRepository.save(approval);
    }

    private int calculateTotalApprovalLineCount(final List<ApprovalLineRequestDTO> requestDTOs) {
        final int totalLine = (int) requestDTOs.stream()
                .filter(dto -> dto.getLineType().equals(APPROVAL_TYPE_APPROVAL) ||
                        dto.getLineType().equals(APPROVAL_TYPE_REVIEWER))
                .count();

        if (totalLine == 0) {
            throw new ApprovalLineRequiredException();
        }

        return totalLine;
    }

    private List<ApprovalAttachment> saveApprovalAttachments(final Approval approval, final List<MultipartFile> files) {
        final List<ApprovalAttachment> approvalAttachments = files.stream()
                .map(file -> {
                    final String s3Url = s3Uploader.upload("sero/documents/", file);

                    return new ApprovalAttachment(file.getOriginalFilename(), s3Url, approval);
                })
                .collect(Collectors.toList());

        return approvalAttachmentRepository.saveAll(approvalAttachments);
    }

    private List<ApprovalLine> saveApprovalLines(final Approval approval, final List<ApprovalLineRequestDTO> requestDTOs) {
        final List<Employee> employees = employeeRepository.findByIdIn(requestDTOs.stream()
                .map(ApprovalLineRequestDTO::getApproverId)
                .collect(Collectors.toList()));

        final Map<Integer, Employee> employeeMap = employees.stream()
                .collect(Collectors.toMap(Employee::getId, employee -> employee));

        final int firstSequence = getFirstApprovalLineSequence(requestDTOs);

        final List<ApprovalLine> approvalLines = requestDTOs.stream()
                .map(dto -> {
                    final Employee employee = employeeMap.get(dto.getApproverId());

                    if (employee == null) {
                        throw new EmployeeNotFoundException(dto.getApproverId() + "번의 직원이 존재하지 않습니다.");
                    }

                    final String status = determineInitialStatus(dto, firstSequence);

                    return new ApprovalLine(dto.getLineType(), dto.getSequence(), status, approval, employee);
                })
                .collect(Collectors.toList());

        return approvalLineRepository.saveAll(approvalLines);
    }

    private int getFirstApprovalLineSequence(final List<ApprovalLineRequestDTO> requestDTOs) {
        return requestDTOs.stream()
                .filter(dto -> APPROVAL_TYPE_APPROVAL.equals(dto.getLineType())
                        || APPROVAL_TYPE_REVIEWER.equals(dto.getLineType()))
                .mapToInt(ApprovalLineRequestDTO::getSequence)
                .min()
                .orElseThrow(ApprovalLineRequiredException::new);
    }

    private String determineInitialStatus(ApprovalLineRequestDTO dto, int firstSequence) {
        if (dto.getLineType().equals(APPROVAL_TYPE_APPROVAL) || dto.getLineType().equals(APPROVAL_TYPE_REVIEWER)) {
            return (dto.getSequence() == firstSequence) ? "ALS_RVW" : "ALS_PEND";
        }

        return null;
    }

    private void validateApprovalLines(final List<ApprovalLineRequestDTO> requestDTOs) {
        requestDTOs.forEach(this::validateApprovalLineSequence);

        validateDuplicateApprovalLineSequence(requestDTOs);
    }

    private void validateApprovalLineSequence(final ApprovalLineRequestDTO requestDTO) {
        final String lineType = requestDTO.getLineType();

        if ((lineType.equals(APPROVAL_TYPE_APPROVAL) || lineType.equals(APPROVAL_TYPE_REVIEWER))
                && requestDTO.getSequence() == null) {
            throw new ApprovalLineSequenceRequiredException();
        }

        if ((lineType.equals(APPROVAL_TYPE_RECIPIENT) || lineType.equals(APPROVAL_TYPE_REFERENCE))
                && requestDTO.getSequence() != null) {
            throw new ApprovalLineSequenceNotAllowedException();
        }
    }

    private void validateDuplicateApprovalLineSequence(final List<ApprovalLineRequestDTO> requestDTOs) {
        final Set<Integer> sequenceSet = new HashSet<>();

        for (ApprovalLineRequestDTO dto : requestDTOs) {
            final String lineType = dto.getLineType();

            if (APPROVAL_TYPE_APPROVAL.equals(lineType) || APPROVAL_TYPE_REVIEWER.equals(lineType)) {
                if (!sequenceSet.add(dto.getSequence())) {
                    throw new ApprovalLineSequenceDuplicatedException();
                }
            }
        }
    }

}