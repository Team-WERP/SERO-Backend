package com.werp.sero.production.command.domain.repository;

import com.werp.sero.production.command.domain.aggregate.WorkOrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WOItemRepository extends JpaRepository<WorkOrderItem, Integer> {
    boolean existsByWorkOrder_IdAndProductionPlan_Id(
            int workOrderId,
            int ppId
    );
}
