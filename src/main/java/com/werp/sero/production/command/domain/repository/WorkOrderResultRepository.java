package com.werp.sero.production.command.domain.repository;

import com.werp.sero.production.command.domain.aggregate.WorkOrderResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface WorkOrderResultRepository extends JpaRepository<WorkOrderResult, Integer> {
    @Query("""
    SELECT IFNULL(SUM(A.goodQuantity), 0)
      FROM WorkOrderResult A
      JOIN A.workOrder B
     WHERE B.productionPlan.id = :ppId
    """)
    int sumGoodQuantityByPpId(int ppId);

    boolean existsByWorkOrderId(int woId);

    Optional<WorkOrderResult> findByWorkOrder_Id(int woId);
}
