package com.werp.sero.material.command.infrastructure.service;

import com.werp.sero.material.command.domain.repository.BomRepository;
import com.werp.sero.material.command.infrastructure.repository.JpaBomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * BOM Repository 구현체 (JPA 활용)
 */
@Repository
@RequiredArgsConstructor
public class BomRepositoryImpl implements BomRepository {

    private final JpaBomRepository jpaBomRepository;

    @Override
    @Transactional
    public void deleteByMaterialId(int materialId) {
        jpaBomRepository.deleteByMaterialId(materialId);
    }
}
