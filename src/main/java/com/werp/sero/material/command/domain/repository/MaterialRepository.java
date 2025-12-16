package com.werp.sero.material.command.domain.repository;

import com.werp.sero.material.command.domain.aggregate.Material;

import java.util.Optional;

/**
 * 자재 Command Repository 인터페이스
 */
public interface MaterialRepository {

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
    Optional<Material> findByIdWithBom(int id);

    /**
     * 자재 코드 중복 체크
     */
    boolean existsByMaterialCode(String materialCode);
}
