package com.werp.sero.shipping.command.domain.repository;

import com.werp.sero.shipping.command.domain.aggregate.GoodsIssueItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GoodsIssueItemRepository extends JpaRepository<GoodsIssueItem, Integer> {
    List<GoodsIssueItem> findByGoodsIssueId(int goodsIssueId);

    /**
     * 특정 주문의 모든 출고지시 품목 조회
     */
    @Query("SELECT gii FROM GoodsIssueItem gii WHERE gii.goodsIssue.salesOrder.id = :salesOrderId")
    List<GoodsIssueItem> findByGoodsIssueSalesOrderId(@Param("salesOrderId") int salesOrderId);
}
