package com.werp.sero.approval.command.application.dto;

import com.werp.sero.approval.command.domain.aggregate.ApprovalAttachment;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ApprovalAttachmentResponseDTO {
    @Schema(description = "첨부파일 파일명")
    @NotBlank
    private String name;

    @Schema(description = "첨부파일 S3 url")
    @NotBlank
    private String url;

    public static ApprovalAttachmentResponseDTO of(final ApprovalAttachment approvalAttachment) {
        return new ApprovalAttachmentResponseDTO(approvalAttachment.getName(), approvalAttachment.getUrl());
    }
}