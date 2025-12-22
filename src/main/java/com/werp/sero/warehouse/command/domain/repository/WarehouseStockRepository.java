package com.werp.sero.warehouse.command.domain.repository;

import com.werp.sero.warehouse.command.domain.aggregate.WarehouseStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface WarehouseStockRepository extends JpaRepository<WarehouseStock, Integer> {

<<<<<<< HEAD
    @Query("SELECT A FROM WarehouseStock A " +
           "WHERE A.warehouse.id = :warehouseId " +
           "AND A.material.id = :materialId")
=======
    @Query("SELECT ws FROM WarehouseStock ws " +
           "WHERE ws.warehouse.id = :warehouseId " +
           "AND ws.material.id = :materialId")
>>>>>>> origin/develop
    Optional<WarehouseStock> findByWarehouseIdAndMaterialId(
            @Param("warehouseId") int warehouseId,
            @Param("materialId") int materialId
    );
}
