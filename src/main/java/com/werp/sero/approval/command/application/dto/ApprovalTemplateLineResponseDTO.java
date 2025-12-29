package com.werp.sero.approval.command.application.dto;

import com.werp.sero.approval.command.domain.aggregate.ApprovalTemplateLine;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ApprovalTemplateLineResponseDTO {
    @Schema(description = "결재선 템플릿 ID(PK)")
    private int approvalTemplateLineId;

    @Schema(description = "결재자 ID(PK)")
    private int approverId;

    @Schema(description = "결재 순서")
    private Integer sequence;

    @Schema(description = "결재 타입")
    @NotBlank
    private String lineType;

    @Schema(description = "비고")
    private String note;

    @Schema(description = "결재자 이름")
    private String approverName;

    @Schema(description = "결재자 직책")
    private String approverPositionCode;

    @Schema(description = "결재자 직급")
    private String approverRankCode;

    @Schema(description = "결재자 부서 코드")
    private String approverDepartmentCode;

    @Schema(description = "결재자 부서명")
    private String approverDepartmentName;

    public static ApprovalTemplateLineResponseDTO of(final ApprovalTemplateLine approvalTemplateLine) {
        return ApprovalTemplateLineResponseDTO.builder()
                .approvalTemplateLineId(approvalTemplateLine.getId())
                .approverId(approvalTemplateLine.getEmployee().getId())
                .approverName(approvalTemplateLine.getEmployee().getName())
                .approverPositionCode(approvalTemplateLine.getEmployee().getPositionCode())
                .approverRankCode(approvalTemplateLine.getEmployee().getRankCode())
                .approverDepartmentCode((approvalTemplateLine.getEmployee().getDepartment() == null) ?
                        null : approvalTemplateLine.getEmployee().getDepartment().getDeptCode())
                .approverDepartmentName((approvalTemplateLine.getEmployee().getDepartment() == null) ?
                        null : approvalTemplateLine.getEmployee().getDepartment().getDeptName())
                .sequence(approvalTemplateLine.getSequence())
                .lineType(approvalTemplateLine.getLineType())
                .note(approvalTemplateLine.getNote())
                .build();
    }
}