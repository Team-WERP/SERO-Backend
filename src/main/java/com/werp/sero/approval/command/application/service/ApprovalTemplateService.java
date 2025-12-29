package com.werp.sero.approval.command.application.service;

import com.werp.sero.approval.command.application.dto.ApprovalTemplateCreateRequestDTO;
import com.werp.sero.approval.command.application.dto.ApprovalTemplateResponseDTO;
import com.werp.sero.employee.command.domain.aggregate.Employee;

public interface ApprovalTemplateService {
    ApprovalTemplateResponseDTO registerApprovalTemplate(final Employee employee,
                                                         final ApprovalTemplateCreateRequestDTO requestDTO);

    void deleteApprovalTemplate(final Employee employee, final int approvalTemplateId);
}