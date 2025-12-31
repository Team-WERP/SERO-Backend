package com.werp.sero.order.query.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SOUrgentOrderDTO {
    @Schema(description = "주문 id")
    private int orderId;

    @Schema(description = "주문 번호")
    private String orderCode;

    @Schema(description = "고객사 이름")
    private String clientName;

    @Schema(description = "납기일")
    private String shippedAt;

    @Schema(description = "주문 상태")
    private String status;
}