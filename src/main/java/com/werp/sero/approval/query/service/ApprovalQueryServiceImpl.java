package com.werp.sero.approval.query.service;

import com.werp.sero.approval.exception.*;
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
    private static final List<String> REF_DOC_TYPE = List.of("SO", "PR", "GI");
    private static final List<String> APPROVAL_STATUS_CODES = List.of("AS_ING", "AS_APPR", "AS_RJCT");
    private static final List<String> ALLOWED_PROCESSED_STATUSES = List.of("ALS_APPR", "ALS_RJCT");

    private static final String APPROVAL_TYPE_APPROVAL_CODE = "AT_APPR";
    private static final String APPROVAL_TYPE_REVIEWER_CODE = "AT_RVW";
    private static final String APPROVAL_TYPE_REFERENCE_CODE = "AT_REF";
    private static final String APPROVAL_TYPE_RECIPIENT_CODE = "AT_RCPT";

    private static final List<String> ALLOWED_APPROVER_TYPES = List.of(APPROVAL_TYPE_APPROVAL_CODE, APPROVAL_TYPE_REVIEWER_CODE);

    private final ApprovalMapper approvalMapper;


    @Transactional(readOnly = true)
    @Override
    public ApprovalListResponseDTO getSubmittedApprovals(final Employee employee,
                                                         final SubmittedApprovalFilterRequestDTO filterDTO,
                                                         final Pageable pageable) {
        validateApprovalStatus(filterDTO.getStatus());

        validateRefDocType(filterDTO.getRefDocType());

        final ApprovalFilterDTO approvalFilterDTO = ApprovalFilterDTO.builder()
                .employeeId(employee.getId())
                .approvalStatus(filterDTO.getStatus())
                .keyword(filterDTO.getKeyword())
                .startDate(filterDTO.getStartDate())
                .endDate(filterDTO.getEndDate())
                .refDocType(filterDTO.getRefDocType())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .build();

        final long totalElements = approvalMapper.countSubmittedApprovalsByFilterDTO(approvalFilterDTO);

        final List<ApprovalSummaryResponseDTO> approvals = approvalMapper.findSubmittedApprovalsByFilterDTO(approvalFilterDTO);

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
                                                        final ArchivedApprovalFilterRequestDTO filterDTO,
                                                        final Pageable pageable) {
        validateApprovalStatus(filterDTO.getApprovalStatus());

        validateApproverType(filterDTO.getApprovalType());

        validateProcessedApprovalLineStatus(filterDTO.getApprovalLineStatus());

        validateRefDocType(filterDTO.getRefDocType());

        final ApprovalFilterDTO approvalFilterDTO = ApprovalFilterDTO.builder()
                .employeeId(employee.getId())
                .approvalStatus(filterDTO.getApprovalStatus())
                .keyword(filterDTO.getKeyword())
                .approvalTypeList(
                        (filterDTO.getApprovalType() != null && !filterDTO.getApprovalType().isEmpty())
                                ? List.of(filterDTO.getApprovalType())
                                : null)
                .approvalLineStatusList(
                        (filterDTO.getApprovalLineStatus() != null && !filterDTO.getApprovalLineStatus().isEmpty())
                                ? List.of(filterDTO.getApprovalLineStatus())
                                : null)
                .startDate(filterDTO.getStartDate())
                .endDate(filterDTO.getEndDate())
                .refDocType(filterDTO.getRefDocType())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .build();

        final long totalElements = approvalMapper.countArchivedApprovalsByFilterDTO(approvalFilterDTO);

        final List<ApprovalSummaryResponseDTO> approvals = approvalMapper.findArchivedApprovalsByFilterDTO(approvalFilterDTO);

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
    public ApprovalListResponseDTO getRequestedApprovals(final Employee employee,
                                                         final RequestedApprovalFilterRequestDTO filterDTO,
                                                         final Pageable pageable) {
        validateApproverType(filterDTO.getApprovalType());

        validateRefDocType(filterDTO.getRefDocType());

        final ApprovalFilterDTO approvalFilterDTO = ApprovalFilterDTO.builder()
                .employeeId(employee.getId())
                .keyword(filterDTO.getKeyword())
                .startDate(filterDTO.getStartDate())
                .endDate(filterDTO.getEndDate())
                .refDocType(filterDTO.getRefDocType())
                .isRead(filterDTO.getIsRead())
                .approvalTypeList(
                        (filterDTO.getApprovalType() != null && !filterDTO.getApprovalType().isEmpty())
                                ? List.of(filterDTO.getApprovalType())
                                : null)
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .build();

        final long totalElements = approvalMapper.countRequestedApprovalsByFilterDTO(approvalFilterDTO);

        final List<ApprovalSummaryResponseDTO> approvals = approvalMapper.findRequestedApprovalsByFilterDTO(approvalFilterDTO);

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
                                                        final ReceivedApprovalFilterRequestDTO filterDTO,
                                                        final Pageable pageable) {
        validateRefDocType(filterDTO.getRefDocType());

        final ApprovalFilterDTO approvalFilterDTO = ApprovalFilterDTO.builder()
                .employeeId(employee.getId())
                .keyword(filterDTO.getKeyword())
                .startDate(filterDTO.getStartDate())
                .endDate(filterDTO.getEndDate())
                .refDocType(filterDTO.getRefDocType())
                .isRead(filterDTO.getIsRead())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .build();

        final long totalElements = approvalMapper.countReceivedApprovalsByFilterDTO(approvalFilterDTO);

        final List<ApprovalSummaryResponseDTO> approvals = approvalMapper.findReceivedApprovalsByFilterDTO(approvalFilterDTO);

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
                                                          final ReferencedApprovalFilterRequestDTO filterDTO,
                                                          final Pageable pageable) {
        validateApprovalStatus(filterDTO.getApprovalStatus());

        validateRefDocType(filterDTO.getRefDocType());

        final ApprovalFilterDTO approvalFilterDTO = ApprovalFilterDTO.builder()
                .employeeId(employee.getId())
                .keyword(filterDTO.getKeyword())
                .startDate(filterDTO.getStartDate())
                .endDate(filterDTO.getEndDate())
                .refDocType(filterDTO.getRefDocType())
                .isRead(filterDTO.getIsRead())
                .approvalStatus(filterDTO.getApprovalStatus())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .build();

        final long totalElements = approvalMapper.countReferencedApprovalsByFilterDTO(approvalFilterDTO);

        final List<ApprovalSummaryResponseDTO> approvals = approvalMapper.findReferencedApprovalsByFilterDTO(approvalFilterDTO);

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
                .filter(line -> APPROVAL_TYPE_APPROVAL_CODE.equals(line.getLineType())
                        || APPROVAL_TYPE_REVIEWER_CODE.equals(line.getLineType()))
                .sorted(Comparator.comparingInt(ApprovalLineInfoResponseDTO::getSequence))
                .collect(Collectors.toList());

        final List<ApprovalLineInfoResponseDTO> referenceLines = responseDTO.getTotalApprovalLines().stream()
                .filter(line -> APPROVAL_TYPE_REFERENCE_CODE.equals(line.getLineType()))
                .collect(Collectors.toList());

        final List<ApprovalLineInfoResponseDTO> recipientLines = responseDTO.getTotalApprovalLines().stream()
                .filter(line -> APPROVAL_TYPE_RECIPIENT_CODE.equals(line.getLineType()))
                .collect(Collectors.toList());

        responseDTO.setApprovalLines(approvalLines);
        responseDTO.setReferenceLines(referenceLines);
        responseDTO.setRecipientLines(recipientLines);
        responseDTO.setRefDocId(refId);

        if (myApprovalLine != null) {
            updateApprovalLineViewedAt(myApprovalLine);
        }

        return responseDTO;
    }

    @Transactional(readOnly = true)
    @Override
    public ApprovalLineSummaryInfoResponseDTO getApprovalSummaryInfo(final String approvalCode) {
        final ApprovalLineSummaryInfoResponseDTO responseDTO =
                approvalMapper.findApprovalByApprovalCode(approvalCode);

        if (responseDTO == null) {
            throw new ApprovalNotFoundException();
        }

        final List<ApprovalLineInfoResponseDTO> approvers =
                approvalMapper.findApprovalLineByApprovalCode(approvalCode);

        responseDTO.setApprovers(approvers);

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
                .orElse(null);

        if (responseDTO.getDrafterId() != employee.getId() && approvalLineInfoResponseDTO == null) {
            throw new ApprovalLineAccessDeniedException();
        }

        if (approvalLineInfoResponseDTO != null) {
            validateCurrentApproverSequence(approvalLineInfoResponseDTO);
            validateRecipientApproval(approvalLineInfoResponseDTO);
        }

        return approvalLineInfoResponseDTO;
    }

    private void validateRecipientApproval(final ApprovalLineInfoResponseDTO approvalLineInfoResponseDTO) {
        if (APPROVAL_TYPE_RECIPIENT_CODE.equals(approvalLineInfoResponseDTO.getLineType()) &&
                !"AS_APPR".equals(approvalLineInfoResponseDTO.getStatus())) {
            throw new ApprovalNotFoundException();
        }
    }

    private void validateCurrentApproverSequence(final ApprovalLineInfoResponseDTO approvalLineInfoResponseDTO) {
        if ((APPROVAL_TYPE_APPROVAL_CODE.equals(approvalLineInfoResponseDTO.getLineType())
                || APPROVAL_TYPE_REVIEWER_CODE.equals(approvalLineInfoResponseDTO.getLineType()))
                && "ALS_PEND".equals(approvalLineInfoResponseDTO.getStatus())) {
            throw new ApprovalNotCurrentSequenceException();
        }
    }

    private void validateRefDocType(final String refDocType) {
        if (refDocType != null && !REF_DOC_TYPE.contains(refDocType)) {
            throw new InvalidDocumentTypeException();
        }
    }

    private void validateApprovalStatus(final String status) {
        if (status != null && !APPROVAL_STATUS_CODES.contains(status)) {
            throw new InvalidApprovalStatusException();
        }
    }

    private void validateApproverType(final String approvalType) {
        if (approvalType != null && !ALLOWED_APPROVER_TYPES.contains(approvalType)) {
            throw new InvalidApproverTypeException();
        }
    }

    private void validateProcessedApprovalLineStatus(final String lineStatus) {
        if (lineStatus != null && !ALLOWED_PROCESSED_STATUSES.contains(lineStatus)) {
            throw new InvalidProcessedApprovalLineStatusException();
        }
    }
}