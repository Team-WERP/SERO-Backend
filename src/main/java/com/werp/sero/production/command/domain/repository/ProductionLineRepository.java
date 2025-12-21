package com.werp.sero.production.command.domain.repository;

import com.werp.sero.production.command.domain.aggregate.ProductionLine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductionLineRepository extends JpaRepository<ProductionLine, Integer> {
}
