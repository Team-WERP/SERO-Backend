package com.werp.sero.shipping.command.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@Schema(description = "출고지시서 결재 요청 DTO")
public class GIApprovalRequestDTO {

    @Schema(description = "제목", example = "출고지시 결재 요청 (GI-20251224-001)")
    @NotBlank
    private String title;

    @Schema(description = "내용", example = "출고지시서에 대한 결재를 요청드립니다.")
    @NotBlank
    private String content;

    @Size(min = 1)
    @NotNull(message = "1개 이상의 결재선이 필요합니다.")
    @Valid
    @Schema(description = "결재선 목록")
    private List<ApprovalLineDTO> approvalLines;

    @Getter
    @NoArgsConstructor
    @Schema(description = "결재선 정보")
    public static class ApprovalLineDTO {
        @Schema(description = "결재자 ID(PK)", example = "5")
        private int approverId;

        @Schema(description = "결재 유형 (AT_APPR: 결재, AT_RVW: 협조, AT_REF: 수신, AT_RCPT: 참조)", example = "AT_APPR")
        @NotBlank
        private String lineType;

        @Schema(description = "결재 순서", example = "1")
        private Integer sequence;
    }
}
