package com.werp.sero.material.command.domain.repository;

/**
 * BOM Command Repository 인터페이스
 */
public interface BomRepository {

    /**
     * 자재 ID로 BOM 삭제
     */
    void deleteByMaterialId(int materialId);
}
