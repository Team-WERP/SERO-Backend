package com.werp.sero.approval.query.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ApprovalTemplateLineInfoResponseDTO {
    private int approvalTemplateLineId;

    private int approverId;

    private String approverName;

    private String approverDepartmentCode;

    private String approverDepartmentName;

    private String approverPositionCode;

    private String approverRankCode;

    private Integer sequence;

    private String lineType;

    private String note;
}