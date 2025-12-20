package com.werp.sero.approval.command.application.service;

import com.werp.sero.approval.command.application.dto.ApprovalCreateRequestDTO;
import com.werp.sero.approval.command.application.dto.ApprovalLineRequestDTO;
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
    public void submitForApproval(final Employee employee, final ApprovalCreateRequestDTO requestDTO,
                                  final List<MultipartFile> files) {
        final Object ref = validateRefCode(requestDTO.getApprovalTargetType(), requestDTO.getRefCode());

        final String approvalCode = documentSequenceCommandService.generateDocumentCode(APPROVAL_DOC_TYPE_CODE);

        final Approval approval = saveApproval(employee, approvalCode, requestDTO);

        if (files != null && !files.isEmpty()) {
            saveApprovalAttachments(approval, files);
        }

        saveApprovalLines(approval, requestDTO.getApprovalLines());

        updateRefCode(requestDTO.getApprovalTargetType(), approvalCode, ref);
    }

    private void updateRefCode(final String approvalTargetType, final String approvalCode,
                               final Object object) {
        switch (approvalTargetType) {
            case "SO" -> ((SalesOrder) object).updateApprovalInfo(approvalCode, "ORD_APPR_PEND");
            case "GI" -> ((GoodsIssue) object).updatedApprovalInfo(approvalCode, "GI_APV_PEND");
            case "PR" -> ((ProductionRequest) object).updateApprovalInfo(approvalCode, "PR_APPR");
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

    private void saveApprovalAttachments(final Approval approval, final List<MultipartFile> files) {
        final List<ApprovalAttachment> approvalAttachments = files.stream()
                .map(file -> {
                    // TODO 파일 S3에 업로드
                    final String s3Url = "https://placehold.co/600x400";

                    return new ApprovalAttachment(file.getOriginalFilename(), s3Url, approval);
                })
                .collect(Collectors.toList());

        approvalAttachmentRepository.saveAll(approvalAttachments);
    }

    private void saveApprovalLines(final Approval approval, final List<ApprovalLineRequestDTO> requestDTOs) {
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

                    return new ApprovalLine(dto.getLineType(), dto.getSequence(), dto.getNote(), approval, employee);
                })
                .collect(Collectors.toList());

        approvalLineRepository.saveAll(approvalLines);
    }
}