package com.werp.sero.production.command.domain.repository;

import com.werp.sero.production.command.domain.aggregate.ProductionPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PPRepository extends JpaRepository<ProductionPlan, Integer> {
    Optional<ProductionPlan> findByProductionRequestItemIdAndStatus(int prItemId, String status);
    boolean existsByProductionRequestItemIdAndStatus(int prItemId, String status);
}
