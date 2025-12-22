package com.werp.sero.approval.command.application.dto;

import com.werp.sero.approval.command.domain.aggregate.Approval;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ApprovalResponseDTO {
    @Schema(description = "결재 ID(PK)")
    private int approvalId;

    @Schema(description = "결재 코드")
    private String approvalCode;

    @Schema(description = "제목")
    private String title;

    @Schema(description = "내용")
    private String content;

    @Schema(description = "문서 번호")
    private String refCode;

    @Schema(description = "기안일시")
    private String draftedAt;

    @Schema(description = "결재 상태")
    private String status;

    @Schema(description = "결재 완료일시")
    private String completedAt;

    @Schema(description = "기안자 ID(PK)")
    private int drafterId;

    @Schema(description = "기안자 이름")
    private String drafterName;

    @Schema(description = "기안자 부서")
    private String drafterDepartment;

    @Schema(description = "기안자 직책")
    private String drafterPosition;

    @Schema(description = "기안자 직급")
    private String drafterRank;

    @Schema(description = "결재 첨부파일 목록")
    private List<ApprovalAttachmentResponseDTO> approvalAttachments;

    @Schema(description = "결재선 목록 (결재/협조)")
    private List<ApprovalLineResponseDTO> approvalLines;

    @Schema(description = "참조자 목록")
    private List<ApprovalLineResponseDTO> referenceLines;

    @Schema(description = "수신자 목록")
    private List<ApprovalLineResponseDTO> recipientLines;

    public static ApprovalResponseDTO of(final Approval approval,
                                         final List<ApprovalAttachmentResponseDTO> approvalAttachments,
                                         final List<ApprovalLineResponseDTO> approvalLines,
                                         final List<ApprovalLineResponseDTO> referenceLines,
                                         final List<ApprovalLineResponseDTO> recipientLines) {
        return ApprovalResponseDTO.builder()
                .approvalId(approval.getId())
                .approvalCode(approval.getApprovalCode())
                .title(approval.getTitle())
                .content(approval.getContent())
                .refCode(approval.getRefCode())
                .draftedAt(approval.getDraftedAt())
                .status(approval.getStatus())
                .completedAt(approval.getCompletedAt())
                .drafterId(approval.getEmployee().getId())
                .drafterName(approval.getEmployee().getName())
                .drafterDepartment((approval.getEmployee().getDepartment() != null) ?
                        approval.getEmployee().getDepartment().getDeptName() : null)
                .drafterPosition(approval.getEmployee().getPositionCode())
                .drafterRank(approval.getEmployee().getRankCode())
                .approvalAttachments(approvalAttachments)
                .approvalLines(approvalLines)
                .referenceLines(referenceLines)
                .recipientLines(recipientLines)
                .build();
    }
}