package com.werp.sero.material.query.dao;

import com.werp.sero.material.query.dto.BomExplosionResponseDTO;
import com.werp.sero.material.query.dto.BomImplosionResponseDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * BOM Query MyBatis Mapper 인터페이스 (조회 전용)
 */
@Mapper
public interface BomMapper {

    /**
     * 정전개: 특정 완제품에 필요한 원부자재 목록 조회
     */
    List<BomExplosionResponseDTO.RequiredMaterial> findByMaterialIdWithRawMaterial(@Param("materialId") int materialId);

    /**
     * 역전개: 특정 원부자재를 사용하는 완제품 목록 조회
     */
    List<BomImplosionResponseDTO.UsedInProduct> findByRawMaterialIdWithMaterial(@Param("rawMaterialId") int rawMaterialId);
}
