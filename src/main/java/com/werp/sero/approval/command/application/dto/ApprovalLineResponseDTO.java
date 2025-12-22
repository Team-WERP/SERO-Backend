package com.werp.sero.approval.command.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.werp.sero.approval.command.domain.aggregate.ApprovalLine;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ApprovalLineResponseDTO {
    @Schema(description = "결재선 ID(PK)")
    private int approvalLineId;

    @Schema(description = "결재자 ID(PK)")
    private int approverId;

    @Schema(description = "결재자 이름")
    private String approverName;

    @Schema(description = "결재자 부서")
    private String approverDepartment;

    @Schema(description = "결재자 직책")
    private String approverPosition;

    @Schema(description = "결재자 직급")
    private String approverRank;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "결재선 순서")
    private Integer sequence;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "결재 상태")
    private String status;

    @Schema(description = "결재 구분")
    private String lineType;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "비고")
    private String note;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "열람일시")
    private String viewedAt;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "결재 처리일시")
    private String processedAt;

    public static ApprovalLineResponseDTO of(final ApprovalLine approvalLine) {
        return ApprovalLineResponseDTO.builder()
                .approvalLineId(approvalLine.getId())
                .approverId(approvalLine.getEmployee().getId())
                .approverName(approvalLine.getEmployee().getName())
                .approverDepartment((approvalLine.getEmployee().getDepartment() != null) ?
                        approvalLine.getEmployee().getDepartment().getDeptName() : null)
                .approverPosition(approvalLine.getEmployee().getPositionCode())
                .approverRank(approvalLine.getEmployee().getRankCode())
                .sequence(approvalLine.getSequence())
                .status(approvalLine.getStatus())
                .lineType(approvalLine.getLineType())
                .note(approvalLine.getNote())
                .viewedAt(approvalLine.getViewedAt())
                .processedAt(approvalLine.getProcessedAt())
                .build();
    }
}