package com.werp.sero.approval.command.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ApprovalAttachmentRequestDTO {
    @Schema(description = "파일명")
    private String originalFileName;

    @Schema(description = "s3 url")
    private String s3Url;
}