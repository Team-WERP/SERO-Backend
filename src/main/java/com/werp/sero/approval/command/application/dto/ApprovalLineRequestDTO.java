package com.werp.sero.approval.command.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ApprovalLineRequestDTO {
    @Schema(description = "결재자 ID(PK)")
    private int approverId;

    @Schema(description = "결재 유형 ex) AT_APPR (결재), AT_RVW (협조), AT_REF (수신), AT_RCPT (참조)", defaultValue = "AT_APPR")
    @NotBlank
    private String lineType;

    @Schema(description = "결재 순서")
    private Integer sequence;

    @Schema(description = "비고")
    private String note;
}