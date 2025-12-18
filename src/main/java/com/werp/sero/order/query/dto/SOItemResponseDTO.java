package com.werp.sero.order.query.dto;

import com.werp.sero.order.command.domain.aggregate.SalesOrderItem;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class SOItemResponseDTO {

    @Schema(description = "주문 품목 ID")
    private int id;

    @Schema(description = "품목 코드")
    private String itemCode;

    @Schema(description = "품목명")
    private String itemName;

    @Schema(description = "규격")
    private String spec;

    @Schema(description = "수량")
    private int quantity;

    @Schema(description = "가용재고")
    private int available_stock;

    @Schema(description = "생산 필요량")
    private int productionRequest;

    @Schema(description = "단위")
    private String unit;

    @Schema(description = "단가")
    private int unitPrice;

    @Schema(description = "총 금액")
    private long totalPrice;

    @Schema(description = "주문 ID")
    private int salesOrderId;

    public static SOItemResponseDTO of(final SalesOrderItem salesOrderItem) {
        return SOItemResponseDTO.builder()
                .id(salesOrderItem.getId())
                .itemCode(salesOrderItem.getItemCode())
                .itemName(salesOrderItem.getItemName())
                .spec(salesOrderItem.getSpec())
                .quantity(salesOrderItem.getQuantity())
                .unit(salesOrderItem.getUnit())
                .unitPrice(salesOrderItem.getUnitPrice())
                .totalPrice(salesOrderItem.getTotalPrice())
                .salesOrderId(salesOrderItem.getSalesOrder().getId())
                .build();
    }
}