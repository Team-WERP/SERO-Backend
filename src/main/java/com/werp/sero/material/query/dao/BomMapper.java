package com.werp.sero.material.query.dao;

import com.werp.sero.material.command.domain.aggregate.Bom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BomMapper extends JpaRepository<Bom, Integer> {

    void deleteByMaterialId(int materialId);

    /**
     * 정전개: 특정 완제품에 필요한 원부자재 목록 조회
     */
    @Query("""
        SELECT b
        FROM Bom b
        JOIN FETCH b.rawMaterial rm
        WHERE b.material.id = :materialId
        ORDER BY b.id
    """)
    List<Bom> findByMaterialIdWithRawMaterial(@Param("materialId") int materialId);

    /**
     * 역전개: 특정 원부자재를 사용하는 완제품 목록 조회
     */
    @Query("""
        SELECT b
        FROM Bom b
        JOIN FETCH b.material m
        WHERE b.rawMaterial.id = :rawMaterialId
        ORDER BY b.id
    """)
    List<Bom> findByRawMaterialIdWithMaterial(@Param("rawMaterialId") int rawMaterialId);
}
