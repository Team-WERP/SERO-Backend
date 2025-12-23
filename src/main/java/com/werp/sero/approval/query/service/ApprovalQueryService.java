package com.werp.sero.approval.query.service;

import com.werp.sero.approval.query.dto.ApprovalFilterRequestDTO;
import com.werp.sero.approval.query.dto.ApprovalListResponseDTO;
import com.werp.sero.employee.command.domain.aggregate.Employee;
import org.springframework.data.domain.Pageable;

public interface ApprovalQueryService {
    ApprovalListResponseDTO getSubmittedApprovals(final Employee employee, final ApprovalFilterRequestDTO filterDTO,
                                                  final Pageable pageable);

    ApprovalListResponseDTO getArchivedApprovals(final Employee employee, final ApprovalFilterRequestDTO filterDTO,
                                                 final Pageable pageable);

    ApprovalListResponseDTO getReceivedApprovals(final Employee employee, final ApprovalFilterRequestDTO filterDTO,
                                                 final Pageable pageable);

    ApprovalListResponseDTO getReferencedApprovals(final Employee employee, final ApprovalFilterRequestDTO filterDTO,
                                                   final Pageable pageable);
}