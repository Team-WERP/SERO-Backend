package com.werp.sero.warehouse.query.service;

import com.werp.sero.warehouse.exception.InvalidMaterialTypeException;
import com.werp.sero.warehouse.exception.InvalidStockStatusException;
import com.werp.sero.warehouse.exception.WarehouseNotFoundException;
import com.werp.sero.warehouse.exception.WarehouseStockNotFoundException;
import com.werp.sero.warehouse.command.domain.aggregate.WarehouseStock;
import com.werp.sero.warehouse.query.dao.WarehouseMapper;
import com.werp.sero.warehouse.query.dao.WarehouseStockHistoryMapper;
import com.werp.sero.warehouse.query.dao.WarehouseStockMapper;
import com.werp.sero.warehouse.query.dto.WarehouseStockDetailResponseDTO;
import com.werp.sero.warehouse.query.dto.WarehouseStockListResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WarehouseStockQueryServiceImpl implements WarehouseStockQueryService {

    private final WarehouseStockMapper warehouseStockMapper;
    private final WarehouseStockHistoryMapper warehouseStockHistoryMapper;
    private final WarehouseMapper warehouseMapper;

    // 허용되는 자재 유형
    private static final List<String> VALID_MATERIAL_TYPES = Arrays.asList("MAT_FG", "MAT_RM");

    // 허용되는 재고 상태
    private static final List<String> VALID_STOCK_STATUSES = Arrays.asList("NORMAL", "LOW", "OUT_OF_STOCK");

    @Override
    public List<WarehouseStockListResponseDTO> getWarehouseStockList(
            Integer warehouseId,
            String materialType,
            String stockStatus,
            String keyword
    ) {
        // 1. 창고 ID 유효성 검증 (값이 있을 경우에만)
        if (warehouseId != null && !warehouseMapper.existsById(warehouseId)) {
            throw new WarehouseNotFoundException();
        }

        // 2. 자재 유형 유효성 검증 (값이 있을 경우에만)
        if (materialType != null && !materialType.isEmpty() && !VALID_MATERIAL_TYPES.contains(materialType)) {
            throw new InvalidMaterialTypeException();
        }

        // 3. 재고 상태 유효성 검증 (값이 있을 경우에만)
        if (stockStatus != null && !stockStatus.isEmpty() && !VALID_STOCK_STATUSES.contains(stockStatus)) {
            throw new InvalidStockStatusException();
        }

        // 4. 재고 목록 조회
        List<WarehouseStock> stocks = warehouseStockMapper.findByCondition(
                warehouseId,
                materialType,
                stockStatus,
                keyword
        );

        return stocks.stream()
                .map(WarehouseStockListResponseDTO::from)
                .toList();
    }

    @Override
    public WarehouseStockDetailResponseDTO getWarehouseStockDetail(int stockId) {
        // 1. 재고 정보 조회
        WarehouseStock stock = warehouseStockMapper.findByIdWithDetails(stockId)
                .orElseThrow(WarehouseStockNotFoundException::new);

        // 2. 변동 이력 조회
        List<WarehouseStockDetailResponseDTO.StockHistoryDTO> histories =
                warehouseStockHistoryMapper.findByWarehouseStockId(stockId);

        return WarehouseStockDetailResponseDTO.from(stock, histories);
    }
}
