package com.werp.sero.order.command.domain.repository;

import com.werp.sero.order.command.domain.aggregate.SalesOrderItemHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SalesOrderItemHistoryRepository extends JpaRepository<SalesOrderItemHistory, Integer> {
    Optional<SalesOrderItemHistory> findTopBySoItemIdOrderByIdDesc(int soItemId);
}
