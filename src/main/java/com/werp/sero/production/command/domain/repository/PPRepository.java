package com.werp.sero.production.command.domain.repository;

import com.werp.sero.production.command.domain.aggregate.ProductionPlan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PPRepository extends JpaRepository<ProductionPlan, Integer> {
}
