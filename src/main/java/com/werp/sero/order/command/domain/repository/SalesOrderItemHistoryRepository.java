package com.werp.sero.order.command.domain.repository;

import com.werp.sero.order.command.domain.aggregate.SalesOrderItemHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SalesOrderItemHistoryRepository extends JpaRepository<SalesOrderItemHistory, Integer> {

    /**
     * 특정 주문 품목의 가장 최신 이력 조회
     */
    @Query("SELECT h FROM SalesOrderItemHistory h WHERE h.soItemId = :soItemId ORDER BY h.id DESC LIMIT 1")
    Optional<SalesOrderItemHistory> findLatestBySoItemId(@Param("soItemId") int soItemId);
}
