package com.werp.sero.production.command.domain.repository;

import com.werp.sero.production.command.domain.aggregate.WorkOrderHistory;
import com.werp.sero.production.command.domain.aggregate.enums.Action;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WorkOrderHistoryRepository extends JpaRepository<WorkOrderHistory, Integer> {
    boolean existsByWorkOrderId(int woId);
    List<WorkOrderHistory> findByWorkOrder_IdOrderByActedAtAsc(int woId);

    Optional<WorkOrderHistory> findTopByWorkOrder_IdAndActionInOrderByActedAtDesc(
            int woId,
            List<Action> actions
    );

}
