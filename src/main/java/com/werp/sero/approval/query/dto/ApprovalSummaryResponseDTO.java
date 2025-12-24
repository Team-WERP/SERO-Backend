package com.werp.sero.approval.query.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ApprovalSummaryResponseDTO {
    private int approvalId;

    private String approvalCode;

    private String title;

    private String approvalStatus;

    private int totalLine;

    private String refType;

    private String refCode;

    private String draftedAt;

    private String drafterId;

    private String drafterName;

    private String drafterPositionCode;

    private String drafterRankCode;

    private String drafterDepartment;

    private String drafterParentDepartment;

    private Boolean isApprovalAttachment;

    private String viewedAt;
}