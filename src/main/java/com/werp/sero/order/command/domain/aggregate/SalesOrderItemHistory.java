package com.werp.sero.order.command.domain.aggregate;

import com.werp.sero.common.util.DateTimeUtils;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Table(name = "sales_order_item_history")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Entity
public class SalesOrderItemHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "pr_quantity", nullable = false, columnDefinition = "int default 0")
    private int prQuantity;

    @Column(name = "pi_quantity", nullable = false, columnDefinition = "int default 0")
    private int piQuantity;

    @Column(name = "gi_quantity", nullable = false, columnDefinition = "int default 0")
    private int giQuantity;

    @Column(name = "shipped_quantity", nullable = false, columnDefinition = "int default 0")
    private int shippedQuantity;

    @Column(name = "do_quantity", nullable = false, columnDefinition = "int default 0")
    private int doQuantity;

    @Column(name = "completed_quantity", nullable = false, columnDefinition = "int default 0")
    private int completedQuantity;

    @Column(name = "so_item_id", nullable = false)
    private int soItemId;

    @Column(name = "created_at", nullable = false)
    private String createdAt;

    @Column(name = "creator_id", nullable = false)
    private int creatorId;
 /**
     * 납품서 생성 시 기납품 수량 이력 생성
     * 주의: doQuantity는 이번 납품서의 수량만 기록 (누적값 아님, SUM으로 조회 시 누적 계산됨)
     */
    public static SalesOrderItemHistory createForDeliveryOrder(
            int soItemId,
            int doQuantity,
            int creatorId,
            String createdAt,
            SalesOrderItemHistory previousHistory
    ) {
        return SalesOrderItemHistory.builder()
                .soItemId(soItemId)
                .doQuantity(doQuantity)  // 이번 납품 수량만 저장 (증가분)
                .prQuantity(0)  // 이번 이벤트와 무관한 수량은 0
                .piQuantity(0)
                .giQuantity(0)
                .shippedQuantity(0)
                .completedQuantity(0)
                .createdAt(createdAt)
                .creatorId(creatorId)
                .build();
    }

    /**
     * 출고지시 작성 시 출고지시 수량 이력 생성
     * 주의: giQuantity는 이번 출고지시의 수량만 기록 (증가분)
     */
    public static SalesOrderItemHistory createForGoodsIssue(
            int soItemId,
            int giQuantity,
            int creatorId,
            String createdAt,
            SalesOrderItemHistory previousHistory
    ) {
        return SalesOrderItemHistory.builder()
                .soItemId(soItemId)
                .giQuantity(giQuantity)  // 이번 출고지시 수량만 저장 (증가분)
                .prQuantity(0)  // 이번 이벤트와 무관한 수량은 0
                .piQuantity(0)
                .doQuantity(0)
                .shippedQuantity(0)
                .completedQuantity(0)
                .createdAt(createdAt)
                .creatorId(creatorId)
                .build();
    }

    /**
     * 출고 완료 처리 시 출고 완료 수량 이력 생성
     * 주의: shippedQuantity는 이번 출고의 수량만 기록 (증가분)
     */
    public static SalesOrderItemHistory createForShipped(
            int soItemId,
            int shippedQuantity,
            int creatorId,
            String createdAt,
            SalesOrderItemHistory previousHistory
    ) {
        return SalesOrderItemHistory.builder()
                .soItemId(soItemId)
                .shippedQuantity(shippedQuantity)  // 이번 출고 수량만 저장 (증가분)
                .prQuantity(0)  // 이번 이벤트와 무관한 수량은 0
                .piQuantity(0)
                .giQuantity(0)
                .doQuantity(0)
                .completedQuantity(0)
                .createdAt(createdAt)
                .creatorId(creatorId)
                .build();
    }

    /**
     * 배송 완료 처리 시 배송 완료 수량 이력 생성
     * 주의: completedQuantity는 이번 배송완료의 수량만 기록 (증가분)
     */
    public static SalesOrderItemHistory createForCompleted(
            int soItemId,
            int completedQuantity,
            int creatorId,
            String createdAt,
            SalesOrderItemHistory previousHistory
    ) {
        return SalesOrderItemHistory.builder()
                .soItemId(soItemId)
                .completedQuantity(completedQuantity)  // 이번 배송완료 수량만 저장 (증가분)
                .prQuantity(0)  // 이번 이벤트와 무관한 수량은 0
                .piQuantity(0)
                .giQuantity(0)
                .doQuantity(0)
                .shippedQuantity(0)
                .createdAt(createdAt)
                .creatorId(creatorId)
                .build();
    }

    /**
     * 생산요청(PR) 등록 시 PR 수량 이력 생성
     * 주의: prQuantity는 이번 생산요청의 수량만 기록 (증가분)
     */
    public static SalesOrderItemHistory createForProductionRequest(
            int soItemId,
            int prQuantity,
            int creatorId,
            SalesOrderItemHistory previousHistory
    ) {
        return SalesOrderItemHistory.builder()
                .soItemId(soItemId)
                .prQuantity(prQuantity)  // 이번 생산요청 수량만 저장 (증가분)
                .piQuantity(0)  // 이번 이벤트와 무관한 수량은 0
                .giQuantity(0)
                .doQuantity(0)
                .shippedQuantity(0)
                .completedQuantity(0)
                .createdAt(DateTimeUtils.nowDateTime())
                .creatorId(creatorId)
                .build();
    }


    /**
     * 생산 실적 등록 시 생산입고(PI) 수량 이력 생성
     * 주의: piQuantity는 이번 생산입고의 수량만 기록 (증가분)
     */
    public static SalesOrderItemHistory createForProductionIn(
            int soItemId,
            int piQuantity,
            int creatorId,
            SalesOrderItemHistory previousHistory
    ) {
        return SalesOrderItemHistory.builder()
                .soItemId(soItemId)
                .piQuantity(piQuantity)  // 이번 생산입고 수량만 저장 (증가분)
                .prQuantity(0)  // 이번 이벤트와 무관한 수량은 0
                .giQuantity(0)
                .doQuantity(0)
                .shippedQuantity(0)
                .completedQuantity(0)
                .createdAt(DateTimeUtils.nowDateTime())
                .creatorId(creatorId)
                .build();
    }
}