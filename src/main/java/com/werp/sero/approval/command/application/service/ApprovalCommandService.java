package com.werp.sero.approval.command.application.service;

import com.werp.sero.approval.command.application.dto.ApprovalCreateRequestDTO;
import com.werp.sero.approval.command.application.dto.ApprovalDecisionRequestDTO;
import com.werp.sero.approval.command.application.dto.ApprovalResponseDTO;
import com.werp.sero.employee.command.domain.aggregate.Employee;

public interface ApprovalCommandService {
    ApprovalResponseDTO submitForApproval(final Employee employee, final ApprovalCreateRequestDTO requestDTO);

    void approve(final Employee employee, final int approvalId, final ApprovalDecisionRequestDTO requestDTO);

    void reject(final Employee employee, final int approvalId, final ApprovalDecisionRequestDTO requestDTO);
}