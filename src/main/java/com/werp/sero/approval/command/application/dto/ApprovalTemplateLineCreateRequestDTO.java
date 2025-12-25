package com.werp.sero.approval.command.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ApprovalTemplateLineCreateRequestDTO {
    @Schema(description = "결재자 ID(PK)")
    private int approverId;

    @Schema(description = "결재 순서")
    private Integer sequence;

    @Schema(description = "결재 타입")
    @NotBlank
    private String lineType;

    @Schema(description = "비고")
    private String note;
}