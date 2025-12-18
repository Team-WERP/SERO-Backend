package com.werp.sero.warehouse.query.dao;

import com.werp.sero.warehouse.query.dto.WarehouseStockDetailResponseDTO;
import com.werp.sero.warehouse.query.dto.WarehouseStockListResponseDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

/**
 * 창고 재고 Query MyBatis Mapper 인터페이스 (조회 전용)
 */
@Mapper
public interface WarehouseStockMapper {

    /**
     * 창고별 재고 목록 조회 (검색 조건 포함)
     */
    List<WarehouseStockListResponseDTO> findByCondition(
            @Param("warehouseId") Integer warehouseId,
            @Param("materialType") String materialType,
            @Param("stockStatus") String stockStatus,
            @Param("keyword") String keyword
    );

    /**
     * 재고 상세 조회 (창고, 자재 정보 포함)
     */
    Optional<WarehouseStockDetailResponseDTO> findByIdWithDetails(@Param("id") int id);

    /**
     * 특정 창고의 특정 자재 재고 조회
     */
    Optional<WarehouseStockDetailResponseDTO> findByWarehouseIdAndMaterialId(
            @Param("warehouseId") int warehouseId,
            @Param("materialId") int materialId
    );
}
