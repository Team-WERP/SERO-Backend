package com.werp.sero.approval.query.service;

import com.werp.sero.approval.exception.ApprovalTemplateNotFoundException;
import com.werp.sero.approval.query.dao.ApprovalTemplateMapper;
import com.werp.sero.approval.query.dto.ApprovalTemplateInfoResponseDTO;
import com.werp.sero.approval.query.dto.ApprovalTemplateLineInfoResponseDTO;
import com.werp.sero.employee.command.domain.aggregate.Employee;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ApprovalTemplateQueryServiceImpl implements ApprovalTemplateQueryService {
    private static final String APPROVAL_TYPE_APPROVAL = "AT_APPR";
    private static final String APPROVAL_TYPE_REVIEWER = "AT_RVW";
    private static final String APPROVAL_TYPE_REFERENCE = "AT_REF";
    private static final String APPROVAL_TYPE_RECIPIENT = "AT_RCPT";

    private final ApprovalTemplateMapper approvalTemplateMapper;

    @Override
    public List<ApprovalTemplateInfoResponseDTO> getApprovalTemplates(final Employee employee) {
        return approvalTemplateMapper.findApprovalTemplatesByEmployee(employee.getId());
    }

    @Override
    public ApprovalTemplateInfoResponseDTO getApprovalTemplateById(Employee employee, int approvalTemplateId) {
        final ApprovalTemplateInfoResponseDTO responseDTO =
                approvalTemplateMapper.findApprovalTemplateById(employee.getId(), approvalTemplateId);

        if (responseDTO == null) {
            throw new ApprovalTemplateNotFoundException();
        }

        final List<ApprovalTemplateLineInfoResponseDTO> approvalLines = responseDTO.getTotalApprovalLines().stream()
                .filter(dto -> APPROVAL_TYPE_APPROVAL.equals(dto.getLineType()) ||
                        APPROVAL_TYPE_REVIEWER.equals(dto.getLineType()))
                .sorted(Comparator.comparingInt(ApprovalTemplateLineInfoResponseDTO::getSequence))
                .collect(Collectors.toList());

        final List<ApprovalTemplateLineInfoResponseDTO> referenceLines = responseDTO.getTotalApprovalLines().stream()
                .filter(dto -> APPROVAL_TYPE_REFERENCE.equals(dto.getLineType()))
                .collect(Collectors.toList());

        final List<ApprovalTemplateLineInfoResponseDTO> recipientLines = responseDTO.getTotalApprovalLines().stream()
                .filter(dto -> APPROVAL_TYPE_RECIPIENT.equals(dto.getLineType()))
                .collect(Collectors.toList());

        responseDTO.setApprovalLines(approvalLines);
        responseDTO.setReferenceLines(referenceLines);
        responseDTO.setRecipientLines(recipientLines);

        return responseDTO;
    }
}