package com.werp.sero.production.command.domain.repository;

import com.werp.sero.production.command.domain.aggregate.WorkOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WORepository extends JpaRepository<WorkOrder, Integer> {
    boolean existsByProductionPlan_Id(int ppId);
}
