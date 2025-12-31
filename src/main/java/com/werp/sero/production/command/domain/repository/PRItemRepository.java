package com.werp.sero.production.command.domain.repository;

import com.werp.sero.production.command.domain.aggregate.ProductionRequestItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PRItemRepository extends JpaRepository<ProductionRequestItem, Integer> {
    Optional<ProductionRequestItem> findByProductionRequestIdAndSalesOrderItemId(int prId, int soItemId);
    List<ProductionRequestItem> findAllByProductionRequest_Id(int prId);
}
