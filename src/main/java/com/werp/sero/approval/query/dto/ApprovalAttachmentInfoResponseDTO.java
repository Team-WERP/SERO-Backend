package com.werp.sero.approval.query.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ApprovalAttachmentInfoResponseDTO {
    private int approvalAttachmentId;
    private String approvalAttachmentName;
    private String approvalAttachmentUrl;
}