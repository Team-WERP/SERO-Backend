package com.werp.sero.production.command.domain.repository;

import com.werp.sero.production.command.domain.aggregate.ProductionRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PRRepository extends JpaRepository<ProductionRequest, Integer> {
}
