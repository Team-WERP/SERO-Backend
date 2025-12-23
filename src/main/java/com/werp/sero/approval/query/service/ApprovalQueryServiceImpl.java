package com.werp.sero.approval.query.service;

import com.werp.sero.approval.query.dao.ApprovalMapper;
import com.werp.sero.approval.query.dto.ApprovalFilterDTO;
import com.werp.sero.approval.query.dto.ApprovalFilterRequestDTO;
import com.werp.sero.approval.query.dto.ApprovalListResponseDTO;
import com.werp.sero.approval.query.dto.ApprovalSummaryResponseDTO;
import com.werp.sero.employee.command.domain.aggregate.Employee;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ApprovalQueryServiceImpl implements ApprovalQueryService {
    private static final String APPROVAL_IN_PROGRESS_CODE = "AS_ING";
    private static final String APPROVAL_LINE_REJECTED_CODE = "ALS_RJCT";
    private static final String APPROVAL_LINE_APPROVED_CODE = "ALS_APPR";
    private static final String APPROVAL_LINE_REVIEW_CODE = "ALS_RVW";

    private final ApprovalMapper approvalMapper;

    @Transactional(readOnly = true)
    @Override
    public ApprovalListResponseDTO getSubmittedApprovals(final Employee employee,
                                                         final ApprovalFilterRequestDTO filterDTO,
                                                         final Pageable pageable) {
        final ApprovalFilterDTO approvalFilterDTO = ApprovalFilterDTO.builder()
                .approvalRole("drafter")
                .approvalStatus(filterDTO.getStatus())
                .employeeId(employee.getId())
                .keyword(filterDTO.getKeyword())
                .startDate(filterDTO.getStartDate())
                .endDate(filterDTO.getEndDate())
                .refType(filterDTO.getRefType())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .build();

        final long totalElements = approvalMapper.countByFilterDTO(approvalFilterDTO);

        final List<ApprovalSummaryResponseDTO> approvals = approvalMapper.findApprovalsByFilterDTO(approvalFilterDTO);

        final int totalPages = (int) ((totalElements + pageable.getPageSize() - 1) / pageable.getPageSize());

        return ApprovalListResponseDTO.builder()
                .approvals(approvals)
                .totalElements(totalElements)
                .size(pageable.getPageSize())
                .number(pageable.getPageNumber())
                .totalPages(totalPages)
                .build();
    }


    @Transactional(readOnly = true)
    @Override
    public ApprovalListResponseDTO getArchivedApprovals(final Employee employee,
                                                                 final ApprovalFilterRequestDTO filterDTO,
                                                                 final Pageable pageable) {
        final ApprovalFilterDTO approvalFilterDTO = ApprovalFilterDTO.builder()
                .approvalRole("approver")
                .approvalStatus(filterDTO.getStatus())
                .employeeId(employee.getId())
                .keyword(filterDTO.getKeyword())
                .approvalLineStatusList(List.of(APPROVAL_LINE_APPROVED_CODE, APPROVAL_LINE_REJECTED_CODE))
                .startDate(filterDTO.getStartDate())
                .endDate(filterDTO.getEndDate())
                .refType(filterDTO.getRefType())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .build();

        final long totalElements = approvalMapper.countByFilterDTO(approvalFilterDTO);

        final List<ApprovalSummaryResponseDTO> approvals = approvalMapper.findApprovalsByFilterDTO(approvalFilterDTO);

        final int totalPages = (int) ((totalElements + pageable.getPageSize() - 1) / pageable.getPageSize());

        return ApprovalListResponseDTO.builder()
                .approvals(approvals)
                .totalElements(totalElements)
                .size(pageable.getPageSize())
                .number(pageable.getPageNumber())
                .totalPages(totalPages)
                .build();
    }

    @Transactional(readOnly = true)
    @Override
    public ApprovalListResponseDTO getReceivedApprovals(final Employee employee,
                                                                 final ApprovalFilterRequestDTO filterDTO,
                                                                 final Pageable pageable) {
        final ApprovalFilterDTO approvalFilterDTO = ApprovalFilterDTO.builder()
                .approvalRole("approver")
                .approvalStatus(filterDTO.getStatus())
                .employeeId(employee.getId())
                .keyword(filterDTO.getKeyword())
                .startDate(filterDTO.getStartDate())
                .endDate(filterDTO.getEndDate())
                .refType(filterDTO.getRefType())
                .isRead(filterDTO.getIsRead())
                .approvalStatus(APPROVAL_IN_PROGRESS_CODE)
                .approvalLineStatusList(List.of(APPROVAL_LINE_REVIEW_CODE))
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .build();

        final long totalElements = approvalMapper.countByFilterDTO(approvalFilterDTO);

        final List<ApprovalSummaryResponseDTO> approvals = approvalMapper.findApprovalsByFilterDTO(approvalFilterDTO);

        final int totalPages = (int) ((totalElements + pageable.getPageSize() - 1) / pageable.getPageSize());

        return ApprovalListResponseDTO.builder()
                .approvals(approvals)
                .totalElements(totalElements)
                .size(pageable.getPageSize())
                .number(pageable.getPageNumber())
                .totalPages(totalPages)
                .build();
    }
}