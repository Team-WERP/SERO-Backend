package com.werp.sero.approval.query.dao;

import com.werp.sero.approval.query.dto.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ApprovalMapper {
    ApprovalDetailResponseDTO findApprovalByApprovalId(@Param("approvalId") final int approvalId);

    Integer findRefDocIdByRefDocCode(@Param("refDocType") final String refDocType,
                                     @Param("refDocCode") final String refDocCode);

    void updateApprovalLineViewAt(@Param("viewedAt") final String viewedAt,
                                  @Param("approvalLineId") final int approvalLineId);

    long countSubmittedApprovalsByFilterDTO(final ApprovalFilterDTO approvalFilterDTO);

    List<ApprovalSummaryResponseDTO> findSubmittedApprovalsByFilterDTO(final ApprovalFilterDTO approvalFilterDTO);

    long countArchivedApprovalsByFilterDTO(final ApprovalFilterDTO approvalFilterDTO);

    List<ApprovalSummaryResponseDTO> findArchivedApprovalsByFilterDTO(final ApprovalFilterDTO approvalFilterDTO);

    long countRequestedApprovalsByFilterDTO(final ApprovalFilterDTO approvalFilterDTO);

    List<ApprovalSummaryResponseDTO> findRequestedApprovalsByFilterDTO(final ApprovalFilterDTO approvalFilterDTO);

    long countReceivedApprovalsByFilterDTO(final ApprovalFilterDTO approvalFilterDTO);

    List<ApprovalSummaryResponseDTO> findReceivedApprovalsByFilterDTO(final ApprovalFilterDTO approvalFilterDTO);

    List<ApprovalSummaryResponseDTO> findReferencedApprovalsByFilterDTO(final ApprovalFilterDTO approvalFilterDTO);

    long countReferencedApprovalsByFilterDTO(final ApprovalFilterDTO approvalFilterDTO);

    List<ApprovalLineInfoResponseDTO> findApprovalLineByApprovalCode(@Param("approvalCode") final String approvalCode);

    ApprovalLineSummaryInfoResponseDTO findApprovalByApprovalCode(@Param("approvalCode") final String approvalCode);
}