package com.werp.sero.material.command.infrastructure.service;

import com.werp.sero.material.command.domain.repository.BomRepository;
import com.werp.sero.material.query.dao.BomMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * BOM Repository 구현체 (MyBatis Mapper 활용)
 */
@Repository
@RequiredArgsConstructor
public class BomRepositoryImpl implements BomRepository {

    private final BomMapper bomMapper;

    @Override
    public void deleteByMaterialId(int materialId) {
        bomMapper.deleteByMaterialId(materialId);
    }
}
