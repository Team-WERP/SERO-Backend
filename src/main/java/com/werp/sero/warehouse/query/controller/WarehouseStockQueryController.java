package com.werp.sero.warehouse.query.controller;

import com.werp.sero.common.security.AccessType;
import com.werp.sero.common.security.RequirePermission;
import com.werp.sero.common.swagger.ApiErrorResponses;
import com.werp.sero.warehouse.query.dto.WarehouseStockDetailResponseDTO;
import com.werp.sero.warehouse.query.dto.WarehouseStockListResponseDTO;
import com.werp.sero.warehouse.query.service.WarehouseStockQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 재고 관리 컨트롤러 (물류팀 전용)
 */
@Tag(name = "재고 관리", description = "창고별 재고 조회 및 수량 변경 이력 조회 API (물류팀 전용)")
@RestController
@RequestMapping("/warehouse/stocks")
@RequiredArgsConstructor
public class WarehouseStockQueryController {

    private final WarehouseStockQueryService warehouseStockQueryService;

    @Operation(summary = "창고별 재고 목록 조회", description = "창고, 자재 유형, 재고 상태별로 필터링하여 재고 목록을 조회합니다. (물류팀 전용)")
    @ApiErrorResponses.WarehouseStockSearchValidation
    @GetMapping
    @RequirePermission(menu = "MM_WHS", authorities = {"AC_SYS", "AC_WHS"}, accessType = AccessType.READ)
    public List<WarehouseStockListResponseDTO> getWarehouseStocks(
            @Parameter(description = "창고 ID (선택)", example = "1")
            @RequestParam(required = false) Integer warehouseId,

            @Parameter(description = "자재 유형 (MAT_FG: 완제품, MAT_RM: 원부자재)", example = "MAT_FG")
            @RequestParam(required = false) String materialType,

            @Parameter(description = "재고 상태 (NORMAL: 정상, LOW: 부족, OUT_OF_STOCK: 품절)", example = "NORMAL")
            @RequestParam(required = false) String stockStatus,

            @Parameter(description = "검색어 (자재명 또는 자재코드)", example = "브레이크")
            @RequestParam(required = false) String keyword) {

        return warehouseStockQueryService.getWarehouseStockList(
                warehouseId,
                materialType,
                stockStatus,
                keyword
        );
    }

    @Operation(summary = "재고 상세 조회 (수량 변경 이력 포함)", description = "특정 재고의 상세 정보와 입출고 변경 이력을 조회합니다. (물류팀 전용)")
    @ApiErrorResponses.WarehouseStockNotFound
    @GetMapping("/{id}")
    @RequirePermission(menu = "MM_WHS", authorities = {"AC_SYS", "AC_WHS"}, accessType = AccessType.READ)
    public WarehouseStockDetailResponseDTO getWarehouseStock(
            @Parameter(description = "재고 ID", example = "1")
            @PathVariable int id) {
        return warehouseStockQueryService.getWarehouseStockDetail(id);
    }
}
