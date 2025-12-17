package com.werp.sero.production.command.domain.repository;

import com.werp.sero.production.command.domain.aggregate.ProductionRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductionRequestRepository extends JpaRepository<ProductionRequest, Integer> {
}
