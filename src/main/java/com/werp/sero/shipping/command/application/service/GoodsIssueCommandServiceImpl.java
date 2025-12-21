package com.werp.sero.shipping.command.application.service;

import com.werp.sero.common.error.ErrorCode;
import com.werp.sero.common.error.exception.BusinessException;
import com.werp.sero.material.command.domain.aggregate.Material;
import com.werp.sero.material.command.domain.repository.MaterialRepository;
import com.werp.sero.order.command.domain.aggregate.SalesOrderItemHistory;
import com.werp.sero.order.command.domain.repository.SalesOrderItemHistoryRepository;
import com.werp.sero.shipping.command.domain.aggregate.GoodsIssue;
import com.werp.sero.shipping.command.domain.aggregate.GoodsIssueItem;
import com.werp.sero.shipping.command.domain.repository.GoodsIssueItemRepository;
import com.werp.sero.shipping.command.domain.repository.GoodsIssueRepository;
import com.werp.sero.warehouse.command.domain.aggregate.WarehouseStock;
import com.werp.sero.warehouse.command.domain.aggregate.WarehouseStockHistory;
import com.werp.sero.warehouse.command.domain.repository.WarehouseStockHistoryRepository;
import com.werp.sero.warehouse.command.domain.repository.WarehouseStockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GoodsIssueCommandServiceImpl implements GoodsIssueCommandService {

    private final GoodsIssueRepository goodsIssueRepository;
    private final GoodsIssueItemRepository goodsIssueItemRepository;
    private final WarehouseStockRepository warehouseStockRepository;
    private final WarehouseStockHistoryRepository warehouseStockHistoryRepository;
    private final MaterialRepository materialRepository;
    private final SalesOrderItemHistoryRepository salesOrderItemHistoryRepository;

    @Override
    @Transactional
    public void completeGoodsIssue(String giCode) {
        // 1. 출고지시 조회
        GoodsIssue goodsIssue = goodsIssueRepository.findByGiCode(giCode)
                .orElseThrow(() -> new BusinessException(ErrorCode.GOODS_ISSUE_NOT_FOUND));

        // 2. 출고지시 품목 조회
        List<GoodsIssueItem> goodsIssueItems = goodsIssueItemRepository.findByGoodsIssueId(goodsIssue.getId());

        // 3. 실제 재고 차감 및 이력 기록
        List<WarehouseStock> stocksToUpdate = new ArrayList<>();
        List<WarehouseStockHistory> historiesToSave = new ArrayList<>();
        List<SalesOrderItemHistory> salesHistoriesToSave = new ArrayList<>();

        for (GoodsIssueItem giItem : goodsIssueItems) {
            String itemCode = giItem.getSalesOrderItem().getItemCode();
            int quantity = giItem.getQuantity();

            // 자재 조회
            Material material = materialRepository.findByMaterialCode(itemCode)
                    .orElseThrow(() -> new BusinessException(ErrorCode.MATERIAL_NOT_FOUND));

            // 창고 재고 조회
            WarehouseStock stock = warehouseStockRepository
                    .findByWarehouseIdAndMaterialId(goodsIssue.getWarehouse().getId(), material.getId())
                    .orElseThrow(() -> new BusinessException(ErrorCode.WAREHOUSE_STOCK_NOT_FOUND));

            // 실제 재고 차감 (current_stock 감소)
            stock.deductStock(quantity);
            stocksToUpdate.add(stock);

            // 창고 재고 변동 이력 기록
            WarehouseStockHistory history = WarehouseStockHistory.builder()
                    .warehouseStockId(stock.getId())
                    .type("OUTBOUND")  // 출고
                    .reason(String.format("출고지시(%s) 완료", giCode))
                    .changedQuantity(-quantity)  // 음수로 표기 (감소)
                    .currentStock(stock.getCurrentStock())  // 변경 후 재고
                    .createdAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                    .build();
            historiesToSave.add(history);

            // 주문 품목별 이력 기록 (출고 완료 수량)
            SalesOrderItemHistory salesHistory = SalesOrderItemHistory.builder()
                    .prQuantity(0)
                    .piQuantity(0)
                    .giQuantity(0)
                    .shippedQuantity(quantity)  // 출고 완료 수량
                    .doQuantity(0)
                    .completedQuantity(0)
                    .soItemId(giItem.getSalesOrderItem().getId())
                    .createdAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                    .creatorId(0)  // TODO: 시스템 사용자 ID 또는 현재 사용자 ID
                    .build();
            salesHistoriesToSave.add(salesHistory);
        }

        // 4. 배치 저장
        warehouseStockRepository.saveAll(stocksToUpdate);
        warehouseStockHistoryRepository.saveAll(historiesToSave);
        salesOrderItemHistoryRepository.saveAll(salesHistoriesToSave);

        // 5. 출고지시 상태를 완료로 변경
        goodsIssue.completeGoodsIssue();
        goodsIssueRepository.save(goodsIssue);
    }
}
