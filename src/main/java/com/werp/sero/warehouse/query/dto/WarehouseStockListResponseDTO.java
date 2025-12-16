package com.werp.sero.warehouse.query.dto;

import com.werp.sero.warehouse.command.domain.aggregate.WarehouseStock;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 창고별 재고 목록 조회 응답 DTO
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WarehouseStockListResponseDTO {

    private int id;
    private int warehouseId;
    private String warehouseName;
    private String warehouseType;
    private int materialId;
    private String materialName;
    private String materialCode;
    private String materialSpec;
    private String materialType;
    private String baseUnit;
    private int safetyStock;
    private int currentStock;
    private int availableStock;
    private String stockStatus; // NORMAL, LOW, OUT_OF_STOCK

    public static WarehouseStockListResponseDTO from(WarehouseStock warehouseStock) {
        String stockStatus = determineStockStatus(
                warehouseStock.getCurrentStock(),
                warehouseStock.getSafetyStock()
        );

        return WarehouseStockListResponseDTO.builder()
                .id(warehouseStock.getId())
                .warehouseId(warehouseStock.getWarehouse().getId())
                .warehouseName(warehouseStock.getWarehouse().getName())
                .warehouseType(warehouseStock.getWarehouse().getType())
                .materialId(warehouseStock.getMaterial().getId())
                .materialName(warehouseStock.getMaterial().getName())
                .materialCode(warehouseStock.getMaterial().getMaterialCode())
                .materialSpec(warehouseStock.getMaterial().getSpec())
                .materialType(warehouseStock.getMaterial().getType())
                .baseUnit(warehouseStock.getMaterial().getBaseUnit())
                .safetyStock(warehouseStock.getSafetyStock())
                .currentStock(warehouseStock.getCurrentStock())
                .availableStock(warehouseStock.getAvailableStock())
                .stockStatus(stockStatus)
                .build();
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
