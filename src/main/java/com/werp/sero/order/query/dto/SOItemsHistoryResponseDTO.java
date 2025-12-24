package com.werp.sero.order.query.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SOItemsHistoryResponseDTO {
    @Schema(description = "주문 ID")
    private int orderId;

    @Schema(description = "주문 품목 정보")
    List<SOItemHistoryDTO> items;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class SOItemInfoDTO {
        private int id;
        private String itemCode;
        private String itemName;
        private int quantity;
        private int availableStock;
    }


    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SOItemHistoryDTO {
        @Schema(description = "품목 id")
        private int itemId;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        @Schema(description = "이력 고유 ID")
        private Integer historyId;

        @Schema(description = "품목 정보")
        private SOItemInfoDTO item;

        @Schema(description = "생산 요청 수량")
        private int prQuantity;

        @Schema(description = "생산 입고 수량")
        private int piQuantity;

        @Schema(description = "기납품 수량")
        private int doQuantity;

        @Schema(description = "출고 지시 수량")
        private int giQuantity;

        @Schema(description = "출고 완료 수량")
        private int shippedQuantity;

        @Schema(description = "배송 완료 수량")
        private int completedQuantity;

        @Schema(description = "이력 생성일자")
        private String createdAt;
    }





}
