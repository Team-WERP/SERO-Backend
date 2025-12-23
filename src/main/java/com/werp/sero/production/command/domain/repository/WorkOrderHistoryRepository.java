package com.werp.sero.production.command.domain.repository;

import com.werp.sero.production.command.domain.aggregate.WorkOrderHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkOrderHistoryRepository extends JpaRepository<WorkOrderHistory, Integer> {
    boolean existsByWorkOrderId(int woId);
}
