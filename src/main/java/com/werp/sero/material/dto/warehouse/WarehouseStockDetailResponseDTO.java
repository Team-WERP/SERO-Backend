package com.werp.sero.material.dto.warehouse;

import com.werp.sero.material.entity.WarehouseStock;
import com.werp.sero.material.entity.WarehouseStockHistory;
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

    public static WarehouseStockDetailResponseDTO from(
            WarehouseStock warehouseStock,
            List<WarehouseStockHistory> histories
    ) {
        String stockStatus = determineStockStatus(
                warehouseStock.getCurrentStock(),
                warehouseStock.getSafetyStock()
        );

        return WarehouseStockDetailResponseDTO.builder()
                .id(warehouseStock.getId())
                .warehouse(WarehouseDTO.from(warehouseStock.getWarehouse()))
                .material(MaterialDTO.from(warehouseStock.getMaterial()))
                .safetyStock(warehouseStock.getSafetyStock())
                .currentStock(warehouseStock.getCurrentStock())
                .availableStock(warehouseStock.getAvailableStock())
                .stockStatus(stockStatus)
                .stockHistory(histories.stream()
                        .map(StockHistoryDTO::from)
                        .toList())
                .build();
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class WarehouseDTO {
        private int id;
        private String name;
        private String address;
        private String type;

        public static WarehouseDTO from(com.werp.sero.material.entity.Warehouse warehouse) {
            return WarehouseDTO.builder()
                    .id(warehouse.getId())
                    .name(warehouse.getName())
                    .address(warehouse.getAddress())
                    .type(warehouse.getType())
                    .build();
        }
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

        public static MaterialDTO from(com.werp.sero.material.entity.Material material) {
            return MaterialDTO.builder()
                    .id(material.getId())
                    .name(material.getName())
                    .materialCode(material.getMaterialCode())
                    .spec(material.getSpec())
                    .type(material.getType())
                    .baseUnit(material.getBaseUnit())
                    .unitPrice(material.getUnitPrice())
                    .build();
        }
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

        public static StockHistoryDTO from(WarehouseStockHistory history) {
            return StockHistoryDTO.builder()
                    .id(history.getId())
                    .type(history.getType())
                    .reason(history.getReason())
                    .changedQuantity(history.getChangedQuantity())
                    .currentStock(history.getCurrentStock())
                    .createdAt(history.getCreatedAt())
                    .build();
        }
    }

    private static String determineStockStatus(int currentStock, int safetyStock) {
        if (currentStock <= 0) {
            return "OUT_OF_STOCK";
        } else if (currentStock < safetyStock) {
            return "LOW";
        } else {
            return "NORMAL";
        }
    }
}
