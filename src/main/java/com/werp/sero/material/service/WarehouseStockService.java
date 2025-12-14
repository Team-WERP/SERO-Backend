package com.werp.sero.material.service;

import com.werp.sero.material.dto.WarehouseStockDetailResponseDTO;
import com.werp.sero.material.dto.WarehouseStockListResponseDTO;

import java.util.List;

public interface WarehouseStockService {

    /**
     * 창고별 재고 목록 조회
     */
    List<WarehouseStockListResponseDTO> getWarehouseStockList(
            Integer warehouseId,
            String materialType,
            String stockStatus,
            String keyword
    );

    /**
     * 재고 상세 조회 (변동 이력 포함)
     */
    WarehouseStockDetailResponseDTO getWarehouseStockDetail(int stockId);
}
