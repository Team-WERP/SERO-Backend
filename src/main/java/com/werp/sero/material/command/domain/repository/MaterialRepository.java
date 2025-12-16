package com.werp.sero.material.command.domain.repository;

import com.werp.sero.material.command.domain.aggregate.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 자재 Command Repository 인터페이스
 */
@Repository
public interface MaterialRepository extends JpaRepository<Material, Integer> {

    /**
     * 자재 저장
     */
    Material save(Material material);

    /**
     * 자재 ID로 조회
     */
    Optional<Material> findById(int id);

    /**
     * BOM 포함 자재 조회
     */
    @Query("""
        SELECT A
        FROM Material A
        LEFT JOIN FETCH A.bomList B
        LEFT JOIN FETCH B.rawMaterial
        WHERE A.id = :id
    """)
    Optional<Material> findByIdWithBom(int id);

    /**
     * 자재 코드 중복 체크
     */
    boolean existsByMaterialCode(String materialCode);
}
