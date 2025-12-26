package com.werp.sero.approval.query.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class ApprovalLineSummaryInfoResponseDTO {
    @Schema(description = "결재 ID(PK)")
    private int approvalId;

    @Schema(description = "결재 제목")
    private String title;

    @Schema(description = "결재 상태 (ex AS_ING, AS_APPR, AS_RJCT)")
    private String approvalStatus;

    @Schema(description = "결재 완료일")
    private String completedAt;

    @Schema(description = "총 결재선 수")
    private String totalLine;

    @Schema(description = "기안일시")
    private String draftedAt;

    @Schema(description = "기안자 ID(PK)")
    private int drafterId;

    @Schema(description = "기안자 이름")
    private String drafterName;

    @Schema(description = "기안자 부서명")
    private String drafterDepartment;

    @Schema(description = "기안자 직책 코드")
    private String drafterPositionCode;

    @Schema(description = "기안자 직급 코드")
    private String drafterRankCode;

    @Schema(description = "결재/협조자 목록")
    private List<ApprovalLineInfoResponseDTO> approvers;
}