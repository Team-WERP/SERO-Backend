package com.werp.sero.approval.command.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ApprovalDecisionRequestDTO {
    @Schema(description = "결재 승인 또는 반려에 대한 비고", example = "결재 승인합니다.")
    private String note;
}