package com.werp.sero.warehouse.query.service;


import com.werp.sero.warehouse.query.dto.WarehouseStockDetailResponseDTO;
import com.werp.sero.warehouse.query.dto.WarehouseStockListResponseDTO;

import java.util.List;

public interface WarehouseStockQueryService {

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
