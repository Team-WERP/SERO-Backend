package com.werp.sero.warehouse.command.domain.repository;

import com.werp.sero.warehouse.command.domain.aggregate.WarehouseStockHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WarehouseStockHistoryRepository extends JpaRepository<WarehouseStockHistory, Integer> {
}
