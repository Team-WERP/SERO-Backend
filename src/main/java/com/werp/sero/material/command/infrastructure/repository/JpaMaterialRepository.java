package com.werp.sero.material.command.infrastructure.repository;

import com.werp.sero.material.command.domain.aggregate.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * 자재 JPA Repository (Command용 - CUD)
 */
public interface JpaMaterialRepository extends JpaRepository<Material, Integer> {

    /**
     * BOM 포함 자재 조회
     */
    @Query("SELECT DISTINCT m FROM Material m LEFT JOIN FETCH m.bomList WHERE m.id = :id")
    Optional<Material> findByIdWithBom(@Param("id") int id);

    /**
     * 자재 코드 중복 체크
     */
    boolean existsByMaterialCode(String materialCode);
}
