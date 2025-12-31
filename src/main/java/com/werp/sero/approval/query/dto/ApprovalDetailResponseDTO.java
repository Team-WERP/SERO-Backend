package com.werp.sero.approval.query.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class ApprovalDetailResponseDTO {
    private int approvalId;

    private String approvalCode;

    private String title;

    private String content;

    private String refDocCode;

    private String refDocType;

    private int refDocId;

    private String draftedAt;

    private String status;

    private String completedAt;

    private String totalLine;

    private int drafterId;

    private String drafterName;

    private String drafterDepartment;

    private String drafterPositionCode;

    private String drafterRankCode;

    private List<ApprovalAttachmentInfoResponseDTO> approvalAttachments;

    private List<ApprovalLineInfoResponseDTO> totalApprovalLines;

    private List<ApprovalLineInfoResponseDTO> approvalLines;

    private List<ApprovalLineInfoResponseDTO> referenceLines;

    private List<ApprovalLineInfoResponseDTO> recipientLines;
}