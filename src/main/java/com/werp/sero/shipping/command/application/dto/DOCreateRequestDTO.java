package com.werp.sero.shipping.command.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "납품서 생성 요청 DTO")
public class DOCreateRequestDTO {

    @NotNull
    @Schema(description = "주문 ID", example = "1")
    private Integer soId;

    @NotNull
    @Schema(description = "납기일시", example = "2025-12-25 14:00:00")
    private String shippedAt;

    @Schema(description = "특이사항", example = "파손 주의")
    private String note;

    @Valid
    @Schema(description = "납품 품목 목록 (null이면 주문의 모든 품목을 자동 포함)")
    private List<DOItemRequestDTO> items;
}
