package com.werp.sero.material.controller;

import com.werp.sero.material.dto.WarehouseStockDetailResponseDTO;
import com.werp.sero.material.dto.WarehouseStockListResponseDTO;
import com.werp.sero.material.service.WarehouseStockService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 재고 관리 컨트롤러
 */
@RestController
@RequestMapping("/api/warehouse-stocks")
@RequiredArgsConstructor
public class WarehouseStockController {

    private final WarehouseStockService warehouseStockService;

    /**
     * 창고별 재고 목록 조회
     *
     * @param warehouseId 창고 ID (선택)
     * @param materialType 자재 구분 (선택): MAT_FG(완제품), MAT_RM(원부자재)
     * @param stockStatus 재고 상태 (선택): NORMAL, LOW, OUT_OF_STOCK
     * @param keyword 검색어 (선택): 자재명, 자재코드
     */
    @GetMapping
    public List<WarehouseStockListResponseDTO> getWarehouseStocks(
            @RequestParam(required = false) Integer warehouseId,
            @RequestParam(required = false) String materialType,
            @RequestParam(required = false) String stockStatus,
            @RequestParam(required = false) String keyword) {

        return warehouseStockService.getWarehouseStockList(
                warehouseId,
                materialType,
                stockStatus,
                keyword
        );
    }

    /**
     * 재고 상세 조회 (변동 이력 포함)
     *
     * @param id 재고 ID
     */
    @GetMapping("/{id}")
    public WarehouseStockDetailResponseDTO getWarehouseStock(@PathVariable int id) {
        return warehouseStockService.getWarehouseStockDetail(id);
    }
}
