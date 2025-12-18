package com.werp.sero.warehouse.query.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 재고 상세 조회 응답 DTO (이력 포함)
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WarehouseStockDetailResponseDTO {

    private int id;
    private WarehouseDTO warehouse;
    private MaterialDTO material;
    private int safetyStock;
    private int currentStock;
    private int availableStock;
    private String stockStatus;
    private List<StockHistoryDTO> stockHistory;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class WarehouseDTO {
        private int id;
        private String name;
        private String address;
        private String type;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class MaterialDTO {
        private int id;
        private String name;
        private String materialCode;
        private String spec;
        private String type;
        private String baseUnit;
        private Long unitPrice;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class StockHistoryDTO {
        private int id;
        private String type;
        private String reason;
        private int changedQuantity;
        private int currentStock;
        private String createdAt;
    }

}
