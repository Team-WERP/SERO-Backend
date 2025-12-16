package com.werp.sero.warehouse.query.service;

import com.werp.sero.warehouse.exception.WarehouseStockNotFoundException;
import com.werp.sero.warehouse.command.domain.aggregate.WarehouseStock;
import com.werp.sero.warehouse.command.domain.aggregate.WarehouseStockHistory;
import com.werp.sero.warehouse.query.dao.WarehouseStockHistoryMapper;
import com.werp.sero.warehouse.query.dao.WarehouseStockMapper;
import com.werp.sero.warehouse.query.dto.WarehouseStockDetailResponseDTO;
import com.werp.sero.warehouse.query.dto.WarehouseStockListResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WarehouseStockQueryServiceImpl implements WarehouseStockQueryService {

    private final WarehouseStockMapper warehouseStockMapper;
    private final WarehouseStockHistoryMapper warehouseStockHistoryMapper;

    @Override
    public List<WarehouseStockListResponseDTO> getWarehouseStockList(
            Integer warehouseId,
            String materialType,
            String stockStatus,
            String keyword
    ) {
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
        Object WarehouseStockNotFoundException;
        WarehouseStock stock = warehouseStockMapper.findByIdWithDetails(stockId)
                .orElseThrow(WarehouseStockNotFoundException::new);

        // 2. 변동 이력 조회
        List<WarehouseStockHistory> histories =
                warehouseStockHistoryMapper.findByWarehouseStockId(stockId);

        return WarehouseStockDetailResponseDTO.from(stock, histories);
    }
}
