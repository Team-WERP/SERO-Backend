package com.werp.sero.material.query.dao;

import com.werp.sero.material.command.domain.aggregate.Material;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MaterialMapper extends JpaRepository<Material, Integer> {
    @Query("""
        SELECT m
        FROM Material m
        WHERE (:type IS NULL OR m.type = :type)
          AND (:status IS NULL OR m.status = :status)
          AND (
            :keyword IS NULL OR
            m.name LIKE CONCAT('%', :keyword, '%') OR
            m.materialCode LIKE CONCAT('%', :keyword, '%')
          )
        ORDER BY m.id DESC
    """)
    List<Material> findByCondition(
            @Param("type") String type,
            @Param("status") String status,
            @Param("keyword") String keyword
    );

    @Query("""
        SELECT DISTINCT m
        FROM Material m
        LEFT JOIN FETCH m.bomList
        WHERE m.id = :id
    """)
    Optional<Material> findByIdWithBom(@Param("id") int id);

    boolean existsByMaterialCode(String materialCode);
}
