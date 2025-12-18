package com.werp.sero.warehouse.query.controller;

import com.werp.sero.warehouse.command.domain.aggregate.Warehouse;
import com.werp.sero.warehouse.query.dao.WarehouseMapper;
import com.werp.sero.warehouse.query.dto.WarehouseListResponseDTO;
import com.werp.sero.warehouse.query.service.WarehouseListQueryService;
import com.werp.sero.warehouse.query.service.WarehouseListQueryServiceImpl;
import com.werp.sero.warehouse.query.service.WarehouseStockQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 창고 조회 컨트롤러
 */
@Tag(name = "창고 관리", description = "창고 조회 API")
@RestController
@RequestMapping("/warehouse/warehouses")
@RequiredArgsConstructor
public class WarehouseQueryController {

    private final WarehouseListQueryService warehouseListQueryService;

    @Operation(summary = "창고 목록 조회", description = "모든 창고 목록을 조회합니다.")
    @GetMapping
    public List<WarehouseListResponseDTO> getAllWarehouses() {
        return warehouseListQueryService.getAllWarehouseList();
    }
}
