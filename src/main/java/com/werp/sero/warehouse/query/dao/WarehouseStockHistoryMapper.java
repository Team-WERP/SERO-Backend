package com.werp.sero.warehouse.query.dao;

import com.werp.sero.warehouse.command.domain.aggregate.WarehouseStockHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WarehouseStockHistoryMapper extends JpaRepository<WarehouseStockHistory, Integer> {

    /**
     * 특정 재고의 변동 이력 조회 (최신순)
     */
    @Query("""
        SELECT h
        FROM WarehouseStockHistory h
        WHERE h.warehouseStockId = :warehouseStockId
        ORDER BY h.createdAt DESC
    """)
    List<WarehouseStockHistory> findByWarehouseStockId(@Param("warehouseStockId") int warehouseStockId);
}
