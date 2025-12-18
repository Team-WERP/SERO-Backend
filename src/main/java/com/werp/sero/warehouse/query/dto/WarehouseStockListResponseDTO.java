package com.werp.sero.warehouse.query.dto;

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

}
