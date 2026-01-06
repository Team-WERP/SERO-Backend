package com.werp.sero.material.command.domain.repository;

import com.werp.sero.material.command.domain.aggregate.Bom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * BOM Command Repository 인터페이스
 */
@Repository
public interface BomRepository extends JpaRepository<Bom, Integer> {

    /**
     * 자재 ID로 BOM 목록 조회 (원자재 정보 포함)
     */
    @Query("""
        SELECT b FROM Bom b
        JOIN FETCH b.rawMaterial
        WHERE b.material.id = :materialId
    """)
    List<Bom> findByMaterialIdWithRawMaterial(int materialId);

    /**
     * 자재 ID로 BOM 삭제
     */
    @Modifying
    @Query("DELETE FROM Bom b WHERE b.material.id = :materialId")
    void deleteByMaterialId(int materialId);

    List<Bom> findByMaterial_Id(int materialId);
}
