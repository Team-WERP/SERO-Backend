package com.werp.sero.approval.command.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class ApprovalCreateRequestDTO {
    @Schema(description = "제목")
    @NotBlank
    private String title;

    @Schema(description = "내용")
    @NotBlank
    private String content;

    @Schema(description = "문서 번호")
    @NotBlank
    private String refCode;

    @Schema(description = "문서 유형")
    private String approvalTargetType;

    List<ApprovalLineRequestDTO> approvalLines;
}