package com.werp.sero.approval.query.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class ApprovalTemplateInfoResponseDTO {
    private int id;

    private String name;

    private String description;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ApprovalTemplateLineInfoResponseDTO> totalApprovalLines;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ApprovalTemplateLineInfoResponseDTO> approvalLines;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ApprovalTemplateLineInfoResponseDTO> referenceLines;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ApprovalTemplateLineInfoResponseDTO> recipientLines;
}