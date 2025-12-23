package com.werp.sero.order.command.domain.repository;

import com.werp.sero.order.command.domain.aggregate.SalesOrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SOItemRepository extends JpaRepository<SalesOrderItem, Integer> {

    List<SalesOrderItem> findBySalesOrderId(int salesOrderId);
}
