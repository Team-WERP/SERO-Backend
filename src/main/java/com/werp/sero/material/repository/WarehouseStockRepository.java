package com.werp.sero.material.repository;

import com.werp.sero.material.entity.WarehouseStock;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface WarehouseStockRepository extends JpaRepository<WarehouseStock, Integer> {

    /**
     * 창고별 재고 목록 조회 (검색 조건 포함)
     */
    @Query("""
        SELECT ws
        FROM WarehouseStock ws
        JOIN FETCH ws.warehouse w
        JOIN FETCH ws.material m
        WHERE (:warehouseId IS NULL OR ws.warehouse.id = :warehouseId)
          AND (:materialType IS NULL OR m.type = :materialType)
          AND (:stockStatus IS NULL OR
               (:stockStatus = 'OUT_OF_STOCK' AND ws.currentStock <= 0) OR
               (:stockStatus = 'LOW' AND ws.currentStock > 0 AND ws.currentStock < ws.safetyStock) OR
               (:stockStatus = 'NORMAL' AND ws.currentStock >= ws.safetyStock))
          AND (:keyword IS NULL OR
               m.name LIKE CONCAT('%', :keyword, '%') OR
               m.materialCode LIKE CONCAT('%', :keyword, '%'))
        ORDER BY ws.id DESC
    """)
    List<WarehouseStock> findByCondition(
            @Param("warehouseId") Integer warehouseId,
            @Param("materialType") String materialType,
            @Param("stockStatus") String stockStatus,
            @Param("keyword") String keyword
    );

    /**
     * 재고 상세 조회 (창고, 자재 정보 포함)
     */
    @Query("""
        SELECT DISTINCT ws
        FROM WarehouseStock ws
        JOIN FETCH ws.warehouse w
        JOIN FETCH ws.material m
        WHERE ws.id = :id
    """)
    Optional<WarehouseStock> findByIdWithDetails(@Param("id") int id);

    /**
     * 특정 창고의 특정 자재 재고 조회
     */
    @Query("""
        SELECT ws
        FROM WarehouseStock ws
        WHERE ws.warehouse.id = :warehouseId
          AND ws.material.id = :materialId
    """)
    Optional<WarehouseStock> findByWarehouseIdAndMaterialId(
            @Param("warehouseId") int warehouseId,
            @Param("materialId") int materialId
    );
}
