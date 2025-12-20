package com.werp.sero.production.command.domain.repository;

import com.werp.sero.production.command.domain.aggregate.ProductionRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PRRepository extends JpaRepository<ProductionRequest, Integer> {
    Optional<ProductionRequest> findByPrCode(final String prCode);
}
