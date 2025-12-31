package com.werp.sero.approval.query.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ApprovalSummaryResponseDTO {
    @Schema(description = "결재 ID(PK)")
    private int approvalId;

    @Schema(description = "결재 코드")
    private String approvalCode;

    @Schema(description = "결재 제목")
    private String title;

    @Schema(description = "결재 상태")
    private String approvalStatus;

    @Schema(description = "결재선 총 수")
    private int totalLine;

    @Schema(description = "승인한 결재자 수")
    private int currentApprovedCount;

    @Schema(description = "현재 검토 중인 결재자 이름")
    private String currentApproverName;

    @Schema(description = "결재 반려 처리한 결재자 이름")
    private String rejecterName;

    @Schema(description = "참조 문서 타입")
    private String refDocType;

    @Schema(description = "참조 문서 코드")
    private String refDocCode;

    @Schema(description = "기안일시")
    private String draftedAt;

    @Schema(description = "기안자 ID(PK)")
    private int drafterId;

    @Schema(description = "기안자 이름")
    private String drafterName;

    @Schema(description = "기안자 직책")
    private String drafterPositionCode;

    @Schema(description = "기안자 직급")
    private String drafterRankCode;

    @Schema(description = "기안자 부서")
    private String drafterDepartment;

    @Schema(description = "첨부파일 여부")
    private Boolean isApprovalAttachment;

    @Schema(description = "열람일시")
    private String viewedAt;

    @Schema(description = "완료일시")
    private String completedAt;
}