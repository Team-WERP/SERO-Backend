package com.werp.sero.approval.command.application.service;

import com.werp.sero.approval.command.application.dto.*;
import com.werp.sero.approval.command.domain.aggregate.Approval;
import com.werp.sero.approval.command.domain.aggregate.ApprovalAttachment;
import com.werp.sero.approval.command.domain.aggregate.ApprovalLine;
import com.werp.sero.approval.command.domain.repository.ApprovalAttachmentRepository;
import com.werp.sero.approval.command.domain.repository.ApprovalLineRepository;
import com.werp.sero.approval.command.domain.repository.ApprovalRepository;
import com.werp.sero.approval.exception.InvalidApprovalTargetTypeException;
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

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ApprovalCommandServiceImpl implements ApprovalCommandService {
    private static final String APPROVAL_DOC_TYPE_CODE = "DOC_SERO";

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
                .filter(approvalLine -> approvalLine.getLineType().equals("AT_APPR") || approvalLine.getLineType().equals("AT_RVW"))
                .sorted(Comparator.comparingInt(ApprovalLine::getSequence))
                .map(ApprovalLineResponseDTO::of)
                .collect(Collectors.toList());

        final List<ApprovalLineResponseDTO> refLines = approvalLines.stream()
                .filter(approvalLine -> approvalLine.getLineType().equals("AT_REF"))
                .map(ApprovalLineResponseDTO::of)
                .collect(Collectors.toList());

        final List<ApprovalLineResponseDTO> rcptLines = approvalLines.stream()
                .filter(approvalLine -> approvalLine.getLineType().equals("AT_RCPT"))
                .map(ApprovalLineResponseDTO::of)
                .collect(Collectors.toList());

        updateRefCode(requestDTO.getApprovalTargetType(), approvalCode, ref);

        return ApprovalResponseDTO.of(approval, approvalAttachmentResponseDTOs, approvalLineResponseDTOs, refLines, rcptLines);
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
                .orElseThrow(InvalidApprovalTargetTypeException::new)
                .validate(refCode);
    }

    private Approval saveApproval(final Employee employee, final String approvalCode,
                                  final ApprovalCreateRequestDTO requestDTO) {
        final Approval approval = new Approval(approvalCode, requestDTO.getTitle(), requestDTO.getContent(),
                requestDTO.getApprovalLines().size(), requestDTO.getRefCode(), DateTimeUtils.nowDateTime(), employee);

        return approvalRepository.save(approval);
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

        final List<ApprovalLine> approvalLines = requestDTOs.stream()
                .map(dto -> {
                    final Employee employee = employeeMap.get(dto.getApproverId());

                    if (employee == null) {
                        throw new EmployeeNotFoundException(dto.getApproverId() + "번의 직원이 존재하지 않습니다.");
                    }

                    String status = null;

                    if (dto.getLineType().equals("AT_APPR") || dto.getLineType().equals("AT_RVW")) {
                        status = "ALS_PEND";
                    }

                    return new ApprovalLine(dto.getLineType(), dto.getSequence(), status, dto.getNote(), approval, employee);
                })
                .collect(Collectors.toList());

        return approvalLineRepository.saveAll(approvalLines);
    }
}