package com.werp.sero.approval.query.dao;

import com.werp.sero.approval.query.dto.ApprovalTemplateInfoResponseDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ApprovalTemplateMapper {
    List<ApprovalTemplateInfoResponseDTO> findApprovalTemplatesByEmployee(@Param("employeeId") final int employeeId);

    ApprovalTemplateInfoResponseDTO findApprovalTemplateById(@Param("employeeId") final int employeeId,
                                                               @Param("approvalTemplateId") final int approvalTemplateId);
}