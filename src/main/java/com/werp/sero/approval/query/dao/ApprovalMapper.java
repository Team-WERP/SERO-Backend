package com.werp.sero.approval.query.dao;

import com.werp.sero.approval.query.dto.ApprovalDetailResponseDTO;
import com.werp.sero.approval.query.dto.ApprovalFilterDTO;
import com.werp.sero.approval.query.dto.ApprovalSummaryResponseDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ApprovalMapper {
    long countByFilterDTO(final ApprovalFilterDTO filterDTO);

    List<ApprovalSummaryResponseDTO> findApprovalsByFilterDTO(final ApprovalFilterDTO approvalFilterDTO);

    ApprovalDetailResponseDTO findApprovalByApprovalId(@Param("approvalId") final int approvalId);

    Integer findRefDocIdByRefDocCode(@Param("refDocType") final String refDocType,
                                     @Param("refDocCode") final String refDocCode);

    void updateApprovalLineViewAt(@Param("viewedAt") final String viewedAt,
                                  @Param("approvalLineId") final int approvalLineId);
}