package com.werp.sero.order.query.dto;

import com.fasterxml.jackson.core.JsonToken;
import com.werp.sero.order.command.domain.aggregate.SalesOrderItem;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class SOClientItemResponseDTO {

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

    @Schema(description = "단위")
    private String unit;

    @Schema(description = "단가")
    private int unitPrice;

    @Schema(description = "총 금액")
    private long totalPrice;

    @Schema(description = "주문 ID")
    private int salesOrderId;

    public static SOClientItemResponseDTO fromEntity(SalesOrderItem entity) {
        return SOClientItemResponseDTO.builder()
                .id(entity.getId())
                .itemCode(entity.getItemCode())
                .itemName(entity.getItemName())
                .spec(entity.getSpec())
                .quantity(entity.getQuantity())
                .unit(entity.getUnit())
                .unitPrice(entity.getUnitPrice())
                .totalPrice(entity.getTotalPrice())
                .salesOrderId(entity.getSalesOrder().getId())
                .build();
    }

}