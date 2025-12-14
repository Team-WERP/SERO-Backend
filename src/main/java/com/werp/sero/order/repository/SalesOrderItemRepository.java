package com.werp.sero.order.repository;

import com.werp.sero.order.entity.SalesOrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SalesOrderItemRepository extends JpaRepository<SalesOrderItem, Integer> {
    List<SalesOrderItem> findBySalesOrderId(int orderId);
}
