package com.werp.sero.order.command.domain.repository;

import com.werp.sero.order.command.domain.aggregate.SalesOrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalesOrderItemRepository extends JpaRepository<SalesOrderItem, Integer> {
}
