package com.werp.sero.approval.command.application.service;

import com.werp.sero.approval.command.application.dto.*;
import com.werp.sero.approval.command.domain.aggregate.Approval;
import com.werp.sero.approval.command.domain.aggregate.ApprovalAttachment;
import com.werp.sero.approval.command.domain.aggregate.ApprovalLine;
import com.werp.sero.approval.command.domain.repository.ApprovalAttachmentRepository;
import com.werp.sero.approval.command.domain.repository.ApprovalLineRepository;
import com.werp.sero.approval.command.domain.repository.ApprovalRepository;
import com.werp.sero.approval.exception.*;
import com.werp.sero.common.util.DateTimeUtils;
import com.werp.sero.employee.command.domain.aggregate.Employee;
import com.werp.sero.employee.command.domain.repository.EmployeeRepository;
import com.werp.sero.employee.exception.EmployeeNotFoundException;
import com.werp.sero.order.command.domain.aggregate.SalesOrder;
import com.werp.sero.production.command.domain.aggregate.ProductionRequest;
import com.werp.sero.shipping.command.domain.aggregate.GoodsIssue;
import com.werp.sero.system.command.application.service.DocumentSequenceCommandService;
import lombok.RequiredArgsConstructor;
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

    private final DocumentSequenceCommandService documentSequenceCommandService;

    @Transactional
    @Override
    public ApprovalResponseDTO submitForApproval(final Employee employee, final ApprovalCreateRequestDTO requestDTO,
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

        return ApprovalResponseDTO.of(approval, approvalAttachmentResponseDTOs, approvalLineResponseDTOs, refLines, rcptLines);
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
            case "GI" -> ((GoodsIssue) object).updatedApprovalInfo(approvalCode, "GI_APPR_PEND");
            case "PR" -> ((ProductionRequest) object).updateApprovalInfo(approvalCode, "PR_APPR_PEND");
        }
    }

    private Object validateRefCode(final String approvalTargetType, final String refCode) {
        return approvalRefCodeValidators.stream()
                .filter(validator -> validator.supports(approvalTargetType))
                .findFirst()
                .orElseThrow(InvalidApprovalTypeException::new)
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
                    // TODO 파일 S3에 업로드
                    final String s3Url = "https://placehold.co/600x400";

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