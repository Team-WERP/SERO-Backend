package com.werp.sero.shipping.command.application.service;

import com.werp.sero.employee.command.domain.aggregate.Employee;
import com.werp.sero.notification.command.domain.aggregate.enums.NotificationType;
import com.werp.sero.notification.command.infrastructure.event.NotificationEvent;
import com.werp.sero.order.command.domain.aggregate.SalesOrder;
import com.werp.sero.order.command.domain.aggregate.SalesOrderItem;
import com.werp.sero.order.command.domain.aggregate.SalesOrderItemHistory;
import com.werp.sero.order.command.domain.repository.SalesOrderItemHistoryRepository;
import com.werp.sero.order.command.domain.repository.SORepository;
import com.werp.sero.shipping.command.domain.aggregate.Delivery;
import com.werp.sero.shipping.command.domain.aggregate.GoodsIssueItem;
import com.werp.sero.shipping.command.domain.repository.DeliveryRepository;
import com.werp.sero.shipping.command.domain.repository.GoodsIssueItemRepository;
import com.werp.sero.shipping.exception.DeliveryNotFoundException;
import com.werp.sero.shipping.exception.InvalidDeliveryStatusTransitionException;
import com.werp.sero.shipping.exception.UnauthorizedDeliveryUpdateException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DeliveryCommandServiceImpl implements DeliveryCommandService {

    private final DeliveryRepository deliveryRepository;
    private final GoodsIssueItemRepository goodsIssueItemRepository;
    private final SalesOrderItemHistoryRepository salesOrderItemHistoryRepository;
    private final SORepository soRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public void startDelivery(String giCode, Employee driver) {
        // 1. 배송 조회
        Delivery delivery = deliveryRepository.findByGoodsIssue_GiCode(giCode)
                .orElseThrow(DeliveryNotFoundException::new);

        // 2. 기사 검증
        if (!delivery.getDriverName().equals(driver.getName()) ||
                !delivery.getDriverContact().equals(driver.getContact())) {
            throw new UnauthorizedDeliveryUpdateException();
        }

        // 3. 상태 검증
        if (!delivery.getStatus().equals("SHIP_ISSUED")) {
            throw new InvalidDeliveryStatusTransitionException("배송 시작은 출고 완료 상태에서만 가능합니다.");
        }

        // 4. 배송 시작
        delivery.startDelivery();
        // @Transactional이 자동으로 변경사항 저장 (dirty checking)

        // 5. 출고지시 담당자에게 알림 발송
        if (delivery.getGoodsIssue().getManager() != null) {
            eventPublisher.publishEvent(new NotificationEvent(
                NotificationType.SHIPPING,
                "배송 출발",
                "출고지시 " + giCode + "의 배송이 출발했습니다. (기사: " + driver.getName() + ")",
                delivery.getGoodsIssue().getManager().getId(),
                "/warehouse/goods-issues/" + giCode
            ));
        }
    }

    @Override
    public void completeDelivery(String giCode, Employee driver) {
        // 1. 배송 조회
        Delivery delivery = deliveryRepository.findByGoodsIssue_GiCode(giCode)
                .orElseThrow(DeliveryNotFoundException::new);

        // 2. 기사 검증
        if (!delivery.getDriverName().equals(driver.getName()) ||
                !delivery.getDriverContact().equals(driver.getContact())) {
            throw new UnauthorizedDeliveryUpdateException();
        }

        // 3. 상태 검증
        if (!delivery.getStatus().equals("SHIP_ING")) {
            throw new InvalidDeliveryStatusTransitionException("배송 완료는 배송중 상태에서만 가능합니다.");
        }

        // 4. 배송 완료
        delivery.completeDelivery();

        // 5. 주문 품목별 이력 기록 (배송 완료 수량)
        String createdAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        List<GoodsIssueItem> goodsIssueItems = goodsIssueItemRepository.findByGoodsIssueId(delivery.getGoodsIssue().getId());
        List<SalesOrderItemHistory> histories = new ArrayList<>();

        for (GoodsIssueItem giItem : goodsIssueItems) {
            // 이전 이력 조회
            SalesOrderItemHistory previousHistory = salesOrderItemHistoryRepository
                    .findLatestBySoItemId(giItem.getSalesOrderItem().getId())
                    .orElse(null);

            SalesOrderItemHistory history = SalesOrderItemHistory.createForCompleted(
                    giItem.getSalesOrderItem().getId(),
                    giItem.getQuantity(),
                    driver.getId(),
                    createdAt,
                    previousHistory
            );
            histories.add(history);
        }

        salesOrderItemHistoryRepository.saveAll(histories);

        // 6. 주문 완료 여부 확인 및 상태 업데이트
        checkAndCompleteOrder(delivery.getGoodsIssue().getSalesOrder());

        // 7. 출고지시 담당자에게 알림 발송
        if (delivery.getGoodsIssue().getManager() != null) {
            eventPublisher.publishEvent(new NotificationEvent(
                NotificationType.SHIPPING,
                "배송 도착",
                "출고지시 " + giCode + "의 배송이 완료되었습니다. (기사: " + driver.getName() + ")",
                delivery.getGoodsIssue().getManager().getId(),
                "/warehouse/goods-issues/" + giCode
            ));
        }
    }

    /**
     * 주문별 배송 완료 수량과 주문 수량을 비교하여 주문 완료 처리
     */
    private void checkAndCompleteOrder(SalesOrder salesOrder) {
        // 1. 주문의 모든 품목별 이력 조회
        List<SalesOrderItemHistory> allHistories = salesOrderItemHistoryRepository.findBySalesOrderId(salesOrder.getId());

        // 2. 품목별로 그룹화하여 배송 완료 수량 집계
        java.util.Map<Integer, Integer> completedQuantityByItem = new java.util.HashMap<>();

        for (SalesOrderItemHistory history : allHistories) {
            int soItemId = history.getSoItemId();
            int completedQty = history.getCompletedQuantity();

            completedQuantityByItem.put(soItemId,
                completedQuantityByItem.getOrDefault(soItemId, 0) + completedQty);
        }

        // 3. 모든 품목의 주문 수량과 배송 완료 수량 비교
        boolean allItemsCompleted = goodsIssueItemRepository.findByGoodsIssueSalesOrderId(salesOrder.getId())
            .stream()
            .allMatch(giItem -> {
                SalesOrderItem soItem = giItem.getSalesOrderItem();
                int orderedQuantity = soItem.getQuantity();
                int completedQuantity = completedQuantityByItem.getOrDefault(soItem.getId(), 0);

                return completedQuantity >= orderedQuantity;
            });

        // 4. 모든 품목이 배송 완료되었으면 주문 상태를 완료로 변경
        if (allItemsCompleted) {
            salesOrder.completeOrder();
            soRepository.save(salesOrder);
        }
    }
}
