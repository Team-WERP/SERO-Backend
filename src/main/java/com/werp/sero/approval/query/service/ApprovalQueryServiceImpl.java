package com.werp.sero.approval.query.service;

import com.werp.sero.approval.exception.ApprovalLineAccessDeniedException;
import com.werp.sero.approval.exception.ApprovalNotCurrentSequenceException;
import com.werp.sero.approval.exception.ApprovalNotFoundException;
import com.werp.sero.approval.exception.ApprovalNotSubmittedException;
import com.werp.sero.approval.query.dao.ApprovalMapper;
import com.werp.sero.approval.query.dto.*;
import com.werp.sero.common.util.DateTimeUtils;
import com.werp.sero.employee.command.domain.aggregate.Employee;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ApprovalQueryServiceImpl implements ApprovalQueryService {
    private static final String APPROVAL_IN_PROGRESS_CODE = "AS_ING";
    private static final String APPROVAL_LINE_REJECTED_CODE = "ALS_RJCT";
    private static final String APPROVAL_LINE_APPROVED_CODE = "ALS_APPR";
    private static final String APPROVAL_LINE_REVIEW_CODE = "ALS_RVW";
    private static final String APPROVAL_TYPE_APPROVAL = "AT_APPR";
    private static final String APPROVAL_TYPE_REVIEWER = "AT_RVW";
    private static final String APPROVAL_TYPE_REFERENCE = "AT_REF";
    private static final String APPROVAL_TYPE_RECIPIENT = "AT_RCPT";

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

    @Transactional(readOnly = true)
    @Override
    public ApprovalListResponseDTO getReferencedApprovals(final Employee employee,
                                                          final ApprovalFilterRequestDTO filterDTO,
                                                          final Pageable pageable) {
        final ApprovalFilterDTO approvalFilterDTO = ApprovalFilterDTO.builder()
                .approvalRole(("recipient ".equals(filterDTO.getViewType()) ? "receiver" : "referrer"))
                .employeeId(employee.getId())
                .keyword(filterDTO.getKeyword())
                .startDate(filterDTO.getStartDate())
                .endDate(filterDTO.getEndDate())
                .refType(filterDTO.getRefType())
                .isRead(filterDTO.getIsRead())
                .approvalStatus(("recipient".equals(filterDTO.getViewType()) ? "AS_APPR" : filterDTO.getStatus()))
                .approvalLineType(("recipient".equals(filterDTO.getViewType()) ? APPROVAL_TYPE_RECIPIENT : APPROVAL_TYPE_REFERENCE))
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

    @Transactional
    @Override
    public ApprovalDetailResponseDTO getApprovalInfo(final Employee employee, final int approvalId) {
        final ApprovalDetailResponseDTO responseDTO = findApprovalByApprovalId(approvalId);

        final int refId = findRefDocIdByRefDocCode(responseDTO.getRefDocType(), responseDTO.getRefDocCode());

        final ApprovalLineInfoResponseDTO myApprovalLine = getMyApprovalLine(employee, responseDTO);

        final List<ApprovalLineInfoResponseDTO> approvalLines = responseDTO.getTotalApprovalLines().stream()
                .filter(line -> APPROVAL_TYPE_APPROVAL.equals(line.getLineType())
                        || APPROVAL_TYPE_REVIEWER.equals(line.getLineType()))
                .sorted(Comparator.comparingInt(ApprovalLineInfoResponseDTO::getSequence))
                .collect(Collectors.toList());

        final List<ApprovalLineInfoResponseDTO> referenceLines = responseDTO.getTotalApprovalLines().stream()
                .filter(line -> APPROVAL_TYPE_REFERENCE.equals(line.getLineType()))
                .collect(Collectors.toList());

        final List<ApprovalLineInfoResponseDTO> recipientLines = responseDTO.getTotalApprovalLines().stream()
                .filter(line -> APPROVAL_TYPE_RECIPIENT.equals(line.getLineType()))
                .collect(Collectors.toList());

        responseDTO.setApprovalLines(approvalLines);
        responseDTO.setReferenceLines(referenceLines);
        responseDTO.setRecipientLines(recipientLines);
        responseDTO.setRefDocId(refId);

        updateApprovalLineViewedAt(myApprovalLine);

        return responseDTO;
    }

    private void updateApprovalLineViewedAt(final ApprovalLineInfoResponseDTO myApprovalLine) {
        if (myApprovalLine.getViewedAt() == null) {
            final String now = DateTimeUtils.nowDateTime();
            approvalMapper.updateApprovalLineViewAt(now, myApprovalLine.getApprovalLineId());
            myApprovalLine.setViewedAt(now);
        }
    }

    private ApprovalDetailResponseDTO findApprovalByApprovalId(final int approvalId) {
        final ApprovalDetailResponseDTO responseDTO = approvalMapper.findApprovalByApprovalId(approvalId);

        if (responseDTO == null) {
            throw new ApprovalNotFoundException();
        }

        return responseDTO;
    }

    private int findRefDocIdByRefDocCode(final String refDocType, final String refDocCode) {
        final Integer refId =
                approvalMapper.findRefDocIdByRefDocCode(refDocType, refDocCode);

        if (refId == null) {
            throw new ApprovalNotSubmittedException();
        }

        return refId;
    }

    private ApprovalLineInfoResponseDTO getMyApprovalLine(final Employee employee, final ApprovalDetailResponseDTO responseDTO) {
        final ApprovalLineInfoResponseDTO approvalLineInfoResponseDTO = responseDTO.getTotalApprovalLines().stream()
                .filter(line -> line.getApproverId() == employee.getId())
                .findFirst()
                .orElseThrow(ApprovalLineAccessDeniedException::new);

        if ((APPROVAL_TYPE_APPROVAL.equals(approvalLineInfoResponseDTO.getLineType())
                || APPROVAL_TYPE_REVIEWER.equals(approvalLineInfoResponseDTO.getLineType()))
                && "ALS_PEND".equals(approvalLineInfoResponseDTO.getStatus())) {
            throw new ApprovalNotCurrentSequenceException();
        }

        if (APPROVAL_TYPE_RECIPIENT.equals(approvalLineInfoResponseDTO.getLineType()) &&
                !"AS_APPR".equals(approvalLineInfoResponseDTO.getStatus())) {
            throw new ApprovalNotFoundException();
        }

        return approvalLineInfoResponseDTO;
    }
}