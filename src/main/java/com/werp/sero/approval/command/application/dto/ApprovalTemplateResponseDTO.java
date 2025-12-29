package com.werp.sero.approval.command.application.dto;

import com.werp.sero.approval.command.domain.aggregate.ApprovalTemplate;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ApprovalTemplateResponseDTO {
    @Schema(description = "템플릿 ID(PK)")
    private int approvalTemplateId;

    @Schema(description = "템플릿명")
    private String name;

    @Schema(description = "템플릿 설명")
    private String description;

    @Schema(description = "템플릿 등록일시")
    private String createdAt;

    @Schema(description = "템플릿 수정일시")
    private String updatedAt;

    @Schema(description = "템플릿 등록자 ID(PK)")
    private int employeeId;

    @Schema(description = "템플릿 등록자 이름")
    private String employeeName;

    @Schema(description = "템플릿 결재선 - 결재/협조")
    private List<ApprovalTemplateLineResponseDTO> approvalLines;

    @Schema(description = "템플릿 결재선 - 참조")
    private List<ApprovalTemplateLineResponseDTO> referenceLines;

    @Schema(description = "템플릿 결재선 - 수신")
    private List<ApprovalTemplateLineResponseDTO> recipientLines;

    public static ApprovalTemplateResponseDTO of(final ApprovalTemplate approvalTemplate,
                                                 final List<ApprovalTemplateLineResponseDTO> approvalLines,
                                                 final List<ApprovalTemplateLineResponseDTO> referenceLines,
                                                 final List<ApprovalTemplateLineResponseDTO> recipientLines) {
        return new ApprovalTemplateResponseDTO(approvalTemplate.getId(),
                approvalTemplate.getName(),
                approvalTemplate.getDescription(),
                approvalTemplate.getCreatedAt(),
                approvalTemplate.getUpdatedAt(),
                approvalTemplate.getEmployee().getId(),
                approvalTemplate.getEmployee().getName(),
                approvalLines,
                referenceLines,
                recipientLines);
    }
}