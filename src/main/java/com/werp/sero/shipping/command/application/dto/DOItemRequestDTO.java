package com.werp.sero.shipping.command.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "납품서 품목 요청 DTO")
public class DOItemRequestDTO {

    @NotNull
    @Schema(description = "주문 품목 ID", example = "1")
    private Integer soItemId;

    @Min(value = 1, message = "신규출고요청 수량은 1 이상이어야 합니다")
    @Schema(description = "신규출고요청 수량", example = "100")
    private int doQuantity;
}
