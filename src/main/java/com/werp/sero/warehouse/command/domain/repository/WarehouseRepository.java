package com.werp.sero.warehouse.command.domain.repository;

import com.werp.sero.warehouse.command.domain.aggregate.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WarehouseRepository extends JpaRepository<Warehouse, Integer> {
}
