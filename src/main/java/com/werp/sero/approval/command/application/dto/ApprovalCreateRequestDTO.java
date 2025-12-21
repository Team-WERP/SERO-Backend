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
public class ApprovalCreateRequestDTO {
    @Schema(description = "제목", defaultValue = "OOO 주문 접수 및 진행 결재 요청")
    @NotBlank
    private String title;

    @Schema(description = "내용", defaultValue = "OOO로부터 주문 요청이 접수되어 주문 진행을 위해 결재 요청드립니다.")
    @NotBlank
    private String content;

    @Schema(description = "문서 번호", defaultValue = "SO-20260101-001")
    @NotBlank
    private String refCode;

    @Schema(description = "문서 유형 ex) SO (주문 요청), PR (생산 요청), GI (출고 요청)", defaultValue = "SO")
    @NotBlank
    private String approvalTargetType;

    @Size(min = 1)
    @NotNull(message = "1개 이상의 결재선이 필요합니다.")
    @Valid
    List<ApprovalLineRequestDTO> approvalLines;
}