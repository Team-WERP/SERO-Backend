package com.werp.sero.approval.query.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ApprovalLineInfoResponseDTO {
    private int approvalLineId;

    private int approverId;

    private String approverName;

    private String approverDepartment;

    private String approverPositionCode;

    private String approverRankCode;

    private Integer sequence;

    private String status;

    private String lineType;

    private String note;

    private String viewedAt;

    private String processedAt;
}