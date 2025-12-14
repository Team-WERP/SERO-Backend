package com.werp.sero.order.repository;

import com.werp.sero.order.entity.SalesOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SalesOrderRepository extends JpaRepository<SalesOrder, Integer> {
    @Query("SELECT so FROM SalesOrder so JOIN FETCH so.client")
    List<SalesOrder> findAllWithClient();
}
