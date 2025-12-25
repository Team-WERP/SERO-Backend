package com.werp.sero.production.command.domain.repository;

import com.werp.sero.production.command.domain.aggregate.WorkOrderHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkOrderHistoryRepository extends JpaRepository<WorkOrderHistory, Integer> {
    boolean existsByWorkOrderId(int woId);
    List<WorkOrderHistory> findByWorkOrder_IdOrderByActedAtAsc(int woId);
}
