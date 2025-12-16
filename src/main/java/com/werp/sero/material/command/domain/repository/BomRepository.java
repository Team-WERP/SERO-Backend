package com.werp.sero.material.command.domain.repository;

import com.werp.sero.material.command.domain.aggregate.Bom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * BOM Command Repository 인터페이스
 */
@Repository
public interface BomRepository extends JpaRepository<Bom, Integer> {

    /**
     * 자재 ID로 BOM 삭제
     */
    void deleteByMaterialId(int materialId);
}
