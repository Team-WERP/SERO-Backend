package com.werp.sero.approval.query.service;

import com.werp.sero.approval.query.dto.*;
import com.werp.sero.employee.command.domain.aggregate.Employee;
import org.springframework.data.domain.Pageable;

public interface ApprovalQueryService {
    ApprovalListResponseDTO getSubmittedApprovals(final Employee employee,
                                                  final SubmittedApprovalFilterRequestDTO filterDTO,
                                                  final Pageable pageable);

    ApprovalListResponseDTO getArchivedApprovals(final Employee employee,
                                                 final ArchivedApprovalFilterRequestDTO filterDTO,
                                                 final Pageable pageable);

    ApprovalListResponseDTO getReceivedApprovals(final Employee employee,
                                                 final ReceivedApprovalFilterRequestDTO filterDTO,
                                                 final Pageable pageable);

    ApprovalListResponseDTO getReferencedApprovals(final Employee employee,
                                                   final ReferencedApprovalFilterRequestDTO filterDTO,
                                                   final Pageable pageable);

    ApprovalListResponseDTO getRequestedApprovals(final Employee employee,
                                                  final RequestedApprovalFilterRequestDTO filterDTO,
                                                  final Pageable pageable);

    ApprovalDetailResponseDTO getApprovalInfo(final Employee employee, final int approvalId);

    ApprovalLineSummaryInfoResponseDTO getApprovalSummaryInfo(final String approvalCode);
}