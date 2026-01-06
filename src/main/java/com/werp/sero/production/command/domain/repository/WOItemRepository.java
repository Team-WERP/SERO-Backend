package com.werp.sero.production.command.domain.repository;

import com.werp.sero.production.command.domain.aggregate.WorkOrderItem;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface WOItemRepository extends JpaRepository<WorkOrderItem, Integer> {
    boolean existsByWorkOrder_IdAndProductionPlan_Id(
            int workOrderId,
            int ppId
    );

    @Query("""
        SELECT COALESCE(SUM(A.plannedQuantity), 0)
        FROM WorkOrderItem A
        WHERE A.workOrder.id = :workOrderId
    """)
    int sumPlannedQuantityByWorkOrderId(
            @Param("workOrderId") int workOrderId
    );

    List<WorkOrderItem> findByWorkOrderId(int woId);

    boolean existsByWorkOrder_IdAndProductionRequestItem_IdAndProductionPlanIsNull(int woId, int prItemId);
}
