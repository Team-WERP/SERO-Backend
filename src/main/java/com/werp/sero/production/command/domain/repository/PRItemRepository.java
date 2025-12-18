package com.werp.sero.production.command.domain.repository;

import com.werp.sero.production.command.domain.aggregate.ProductionRequestItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PRItemRepository extends JpaRepository<ProductionRequestItem, Integer> {
}
