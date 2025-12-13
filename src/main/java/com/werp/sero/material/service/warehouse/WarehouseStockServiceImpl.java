package com.werp.sero.material.service.warehouse;

import com.werp.sero.material.dto.warehouse.WarehouseStockDetailResponseDTO;
import com.werp.sero.material.dto.warehouse.WarehouseStockListResponseDTO;
import com.werp.sero.material.entity.WarehouseStock;
import com.werp.sero.material.entity.WarehouseStockHistory;
import com.werp.sero.material.exception.WarehouseStockNotFoundException;
import com.werp.sero.material.repository.WarehouseStockHistoryRepository;
import com.werp.sero.material.repository.WarehouseStockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WarehouseStockServiceImpl implements WarehouseStockService {

    private final WarehouseStockRepository warehouseStockRepository;
    private final WarehouseStockHistoryRepository warehouseStockHistoryRepository;

    @Override
    public List<WarehouseStockListResponseDTO> getWarehouseStockList(
            Integer warehouseId,
            String materialType,
            String stockStatus,
            String keyword
    ) {
        List<WarehouseStock> stocks = warehouseStockRepository.findByCondition(
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
        WarehouseStock stock = warehouseStockRepository.findByIdWithDetails(stockId)
                .orElseThrow(WarehouseStockNotFoundException::new);

        // 2. 변동 이력 조회
        List<WarehouseStockHistory> histories =
                warehouseStockHistoryRepository.findByWarehouseStockId(stockId);

        return WarehouseStockDetailResponseDTO.from(stock, histories);
    }
}
