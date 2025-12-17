package com.werp.sero.warehouse.query.dao;

import com.werp.sero.warehouse.command.domain.aggregate.Warehouse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface WarehouseMapper {

    /**
     * ID로 창고 조회
     */
    Optional<Warehouse> findById(@Param("id") int id);

    /**
     * 창고 ID 존재 여부 확인
     */
    boolean existsById(@Param("id") int id);

    /**
     * 모든 창고 목록 조회
     */
    List<Warehouse> findAll();
}
