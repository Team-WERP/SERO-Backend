package com.werp.sero.approval.command.application.dto;

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
public class ApprovalTemplateCreateRequestDTO {
    @Schema(description = "템플릿명")
    @NotBlank
    private String name;

    @Schema(description = "템플릿 설명")
    private String description;

    @Schema(description = "템플릿 결재선")
    @Size(min = 1)
    @NotNull
    @Valid
    private List<ApprovalTemplateLineCreateRequestDTO> lines;
}