package com.werp.sero.shipping.command.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "출고지시서 결재 요청 응답 DTO")
public class GIApprovalResponseDTO {

    @Schema(description = "출고지시 번호", example = "GI-20251224-001")
    private String giCode;

    @Schema(description = "결재 코드", example = "APV-20251224-001")
    private String approvalCode;

    @Schema(description = "결재 제목", example = "출고지시 결재 요청 (GI-20251224-001)")
    private String title;

    @Schema(description = "결재 상신 일시", example = "2025-12-24 14:30")
    private String submittedAt;

    @Schema(description = "결재 상태", example = "AS_ING")
    private String status;

    @Schema(description = "출고지시 상태", example = "GI_APPR_PEND")
    private String giStatus;
}
