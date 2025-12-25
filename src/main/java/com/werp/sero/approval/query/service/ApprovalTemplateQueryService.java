package com.werp.sero.approval.query.service;

import com.werp.sero.approval.query.dto.ApprovalTemplateInfoResponseDTO;
import com.werp.sero.employee.command.domain.aggregate.Employee;

import java.util.List;

public interface ApprovalTemplateQueryService {
    List<ApprovalTemplateInfoResponseDTO> getApprovalTemplates(final Employee employee);

    ApprovalTemplateInfoResponseDTO getApprovalTemplateById(final Employee employee, final int approvalTemplateId);
}