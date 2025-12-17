package com.werp.sero.warehouse.query.dao;

import com.werp.sero.warehouse.command.domain.aggregate.WarehouseStockHistory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 창고 재고 이력 Query MyBatis Mapper 인터페이스 (조회 전용)
 */
@Mapper
public interface WarehouseStockHistoryMapper {

    /**
     * 특정 재고의 변동 이력 조회 (최신순)
     */
    List<WarehouseStockHistory> findByWarehouseStockId(@Param("warehouseStockId") int warehouseStockId);
}
