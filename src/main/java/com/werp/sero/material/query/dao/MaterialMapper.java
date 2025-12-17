package com.werp.sero.material.query.dao;

import com.werp.sero.material.command.domain.aggregate.Material;
import com.werp.sero.material.query.dto.MaterialWithBomResponseDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

/**
 * 자재 Query MyBatis Mapper 인터페이스 (조회 전용)
 */
@Mapper
public interface MaterialMapper {

    /**
     * 조건별 자재 목록 조회
     */
    List<Material> findByCondition(
            @Param("type") String type,
            @Param("status") String status,
            @Param("keyword") String keyword
    );

    /**
     * ID로 자재 조회 (BOM 포함)
     */
    Optional<MaterialWithBomResponseDTO> findByIdWithBom(@Param("id") int id);

    /**
     * ID로 자재 조회
     */
    Optional<Material> findById(@Param("id") int id);
}
