package com.werp.sero.approval.query.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ApprovalLineInfoResponseDTO {
    @Schema(description = "결재선 ID(PK)")
    private int approvalLineId;

    @Schema(description = "결재자 ID(PK)")
    private int approverId;

    @Schema(description = "결재/협조자 목록")
    private String approverName;

    @Schema(description = "결재자 부서명")
    private String approverDepartment;

    @Schema(description = "결재자 직책 코드")
    private String approverPositionCode;

    @Schema(description = "결재자 직급 코드")
    private String approverRankCode;

    @Schema(description = "결재 순서")
    private Integer sequence;

    @Schema(description = "결재선 상태 (ex ALS_PEND(대기), ALS_RVW(검토), ALS_APPR(승인), ALS_RJCT(반려)")
    private String status;

    @Schema(description = "결재 타입 (ex AT_APPR(결재), AT_RVW(협조), AT_REF(참조), AT_RCPT(수신)")
    private String lineType;

    @Schema(description = "비고")
    private String note;

    @Schema(description = "열람일시")
    private String viewedAt;

    @Schema(description = "결재 처리일시(승인/반려일시)")
    private String processedAt;
}