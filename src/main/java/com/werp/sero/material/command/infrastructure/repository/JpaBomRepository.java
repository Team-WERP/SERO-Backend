package com.werp.sero.material.command.infrastructure.repository;

import com.werp.sero.material.command.domain.aggregate.Bom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * BOM JPA Repository (Command용 - CUD)
 */
public interface JpaBomRepository extends JpaRepository<Bom, Integer> {

    /**
     * 자재 ID로 BOM 삭제
     */
    @Modifying
    @Query("DELETE FROM Bom b WHERE b.material.id = :materialId")
    void deleteByMaterialId(@Param("materialId") int materialId);
}
