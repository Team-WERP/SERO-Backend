package com.werp.sero.order.command.domain.repository;

import com.werp.sero.order.command.domain.aggregate.SalesOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalesOrderRepository extends JpaRepository<SalesOrder, Integer> {
}
