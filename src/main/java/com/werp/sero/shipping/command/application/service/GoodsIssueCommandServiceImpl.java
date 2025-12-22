package com.werp.sero.shipping.command.application.service;

import com.werp.sero.common.error.ErrorCode;
import com.werp.sero.common.error.exception.BusinessException;
<<<<<<< HEAD
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
=======
import com.werp.sero.employee.command.domain.aggregate.Employee;
import com.werp.sero.material.command.domain.aggregate.Material;
import com.werp.sero.material.command.domain.repository.MaterialRepository;
import com.werp.sero.order.command.domain.aggregate.SalesOrder;
import com.werp.sero.order.command.domain.aggregate.SalesOrderItemHistory;
import com.werp.sero.order.command.domain.repository.SalesOrderItemHistoryRepository;
import com.werp.sero.order.command.domain.repository.SORepository;
import com.werp.sero.shipping.command.application.dto.GICreateRequestDTO;
import com.werp.sero.shipping.command.domain.aggregate.DeliveryOrder;
import com.werp.sero.shipping.command.domain.aggregate.DeliveryOrderItem;
import com.werp.sero.shipping.command.domain.aggregate.GoodsIssue;
import com.werp.sero.shipping.command.domain.aggregate.GoodsIssueItem;
import com.werp.sero.shipping.command.domain.repository.DeliveryOrderItemRepository;
import com.werp.sero.shipping.command.domain.repository.DeliveryOrderRepository;
import com.werp.sero.shipping.command.domain.repository.GoodsIssueItemRepository;
import com.werp.sero.shipping.command.domain.repository.GoodsIssueRepository;
import com.werp.sero.shipping.exception.DeliveryOrderNotFoundException;
import com.werp.sero.shipping.exception.GoodsIssueAlreadyExistsException;
import com.werp.sero.system.command.application.service.DocumentSequenceCommandService;
import com.werp.sero.warehouse.command.domain.aggregate.Warehouse;
import com.werp.sero.warehouse.command.domain.aggregate.WarehouseStock;
import com.werp.sero.warehouse.command.domain.repository.WarehouseRepository;
import com.werp.sero.warehouse.command.domain.repository.WarehouseStockRepository;
import com.werp.sero.warehouse.exception.InsufficientStockException;
>>>>>>> origin/develop
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
<<<<<<< HEAD
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

        // 상태 검증: 결재 승인된 출고지시만 출고 처리 가능
        if (!"GI_APV_APPR".equals(goodsIssue.getStatus())) {
            throw new BusinessException(ErrorCode.INVALID_GOODS_ISSUE_STATUS);
        }

        // 2. 출고지시 품목 조회
        List<GoodsIssueItem> goodsIssueItems = goodsIssueItemRepository.findByGoodsIssueId(goodsIssue.getId());

        // 3. 실제 재고 차감 및 이력 기록
        List<WarehouseStock> stocksToUpdate = new ArrayList<>();
        List<WarehouseStockHistory> historiesToSave = new ArrayList<>();
        List<SalesOrderItemHistory> salesHistoriesToSave = new ArrayList<>();

        for (GoodsIssueItem giItem : goodsIssueItems) {
            String itemCode = giItem.getSalesOrderItem().getItemCode();
            int quantity = giItem.getQuantity();
=======
    private final DeliveryOrderRepository deliveryOrderRepository;
    private final DeliveryOrderItemRepository deliveryOrderItemRepository;
    private final WarehouseRepository warehouseRepository;
    private final WarehouseStockRepository warehouseStockRepository;
    private final MaterialRepository materialRepository;
    private final SORepository soRepository;
    private final SalesOrderItemHistoryRepository salesOrderItemHistoryRepository;
    private final DocumentSequenceCommandService documentSequenceCommandService;

    @Override
    @Transactional
    public String createGoodsIssue(GICreateRequestDTO requestDTO, Employee drafter) {
        // 1. 중복 검증 - 이미 출고지시가 생성된 납품서인지 확인
        if (goodsIssueRepository.existsByDoCode(requestDTO.getDoCode())) {
            throw new GoodsIssueAlreadyExistsException();
        }

        // 2. 납품서 조회
        DeliveryOrder deliveryOrder = deliveryOrderRepository.findByDoCode(requestDTO.getDoCode())
                .orElseThrow(DeliveryOrderNotFoundException::new);

        // 3. 출고 창고 조회
        Warehouse warehouse = warehouseRepository.findById(requestDTO.getWarehouseId())
                .orElseThrow(() -> new BusinessException(ErrorCode.WAREHOUSE_NOT_FOUND));

        // 4. 납품서 품목 조회
        List<DeliveryOrderItem> deliveryOrderItems = deliveryOrderItemRepository.findByDeliveryOrderId(deliveryOrder.getId());

        // 5. 주문 조회 (첫 번째 품목의 SalesOrderItem에서 SalesOrder 가져오기)
        if (deliveryOrderItems.isEmpty()) {
            throw new BusinessException(ErrorCode.BAD_REQUEST);
        }
        SalesOrder salesOrder = deliveryOrderItems.get(0).getSalesOrderItem().getSalesOrder();

        // 6. 출고지시 코드 생성
        String giCode = documentSequenceCommandService.generateDocumentCode("DOC_GI");

        // 7. 출고지시 생성
        GoodsIssue goodsIssue = GoodsIssue.builder()
                .giCode(giCode)
                .approvalCode(null)
                .giUrl("")
                .status("GI_RVW")  // 검토 중 상태
                .note(requestDTO.getNote())
                .doCode(requestDTO.getDoCode())
                .createdAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .salesOrder(salesOrder)
                .drafter(drafter)
                .manager(null)  // 초기에는 담당자 미배정 (물류팀에서 "담당하기" 버튼 클릭 시 배정)
                .warehouse(warehouse)
                .build();

        goodsIssueRepository.save(goodsIssue);

        // 8. 재고 검증, 할당 및 출고지시 품목 생성
        List<String> insufficientItems = new ArrayList<>();
        List<GoodsIssueItem> goodsIssueItems = new ArrayList<>();
        List<WarehouseStock> stocksToUpdate = new ArrayList<>();

        for (DeliveryOrderItem doItem : deliveryOrderItems) {
            String itemCode = doItem.getSalesOrderItem().getItemCode();
            String itemName = doItem.getSalesOrderItem().getItemName();
            int requiredQuantity = doItem.getDoQuantity();
>>>>>>> origin/develop

            // 자재 조회
            Material material = materialRepository.findByMaterialCode(itemCode)
                    .orElseThrow(() -> new BusinessException(ErrorCode.MATERIAL_NOT_FOUND));

            // 창고 재고 조회
            WarehouseStock stock = warehouseStockRepository
<<<<<<< HEAD
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

        // 5. 출고지시 상태를 출고완료(GI_ISSUED)로 변경
        goodsIssue.updatedApprovalInfo(goodsIssue.getApprovalCode(), "GI_ISSUED");
        goodsIssueRepository.save(goodsIssue);
=======
                    .findByWarehouseIdAndMaterialId(warehouse.getId(), material.getId())
                    .orElse(null);

            // 재고 검증
            if (stock == null || stock.getAvailableStock() < requiredQuantity) {
                int availableStock = (stock != null) ? stock.getAvailableStock() : 0;
                insufficientItems.add(String.format("%s(%s): 필요 %d개, 가용 %d개",
                        itemName, itemCode, requiredQuantity, availableStock));
                continue;  // 재고 부족 시 다음 품목으로
            }

            // 출고지시 품목 생성
            GoodsIssueItem giItem = GoodsIssueItem.builder()
                    .quantity(requiredQuantity)
                    .goodsIssue(goodsIssue)
                    .salesOrderItem(doItem.getSalesOrderItem())
                    .build();
            goodsIssueItems.add(giItem);

            // 재고 할당 (available_stock 감소)
            stock.allocateStock(requiredQuantity);
            stocksToUpdate.add(stock);
        }

        // 9. 재고 부족 시 예외 발생
        if (!insufficientItems.isEmpty()) {
            String message = "재고가 부족합니다: " + String.join(", ", insufficientItems);
            throw new InsufficientStockException(message);
        }

        // 10. 배치 저장 (출고지시 품목 및 재고)
        goodsIssueItemRepository.saveAll(goodsIssueItems);
        warehouseStockRepository.saveAll(stocksToUpdate);

        // 11. 주문 품목별 이력 기록 (출고지시 수량)
        for (DeliveryOrderItem doItem : deliveryOrderItems) {
            SalesOrderItemHistory history = SalesOrderItemHistory.builder()
                    .prQuantity(0)
                    .piQuantity(0)
                    .giQuantity(doItem.getDoQuantity())  // 출고지시 수량
                    .shippedQuantity(0)
                    .doQuantity(0)
                    .completedQuantity(0)
                    .soItemId(doItem.getSalesOrderItem().getId())
                    .createdAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                    .creatorId(drafter.getId())
                    .build();
            salesOrderItemHistoryRepository.save(history);
        }

        return giCode;
>>>>>>> origin/develop
    }
}
