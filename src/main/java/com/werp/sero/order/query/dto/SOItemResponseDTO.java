package com.werp.sero.order.query.dto;

import com.werp.sero.order.command.domain.aggregate.SalesOrderItem;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
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

    @Schema(description = "단위")
    private String unit;

    @Schema(description = "단가")
    private int unitPrice;

    @Schema(description = "총 금액")
    private long totalPrice;

    @Schema(description = "주문 ID")
    private int salesOrderId;


    public static SOItemResponseDTO of(final SalesOrderItem salesOrderItem) {
        return new SOItemResponseDTO(
                salesOrderItem.getId(),
                salesOrderItem.getItemCode(),
                salesOrderItem.getItemName(),
                salesOrderItem.getSpec(),
                salesOrderItem.getQuantity(),
                salesOrderItem.getUnit(),
                salesOrderItem.getUnitPrice(),
                salesOrderItem.getTotalPrice(),
                salesOrderItem.getSalesOrder().getId()
        );
    }
}
