package com.werp.sero.material.command.infrastructure.service;

import com.werp.sero.material.command.domain.aggregate.Material;
import com.werp.sero.material.command.domain.repository.MaterialRepository;
import com.werp.sero.material.query.dao.MaterialMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 자재 Repository 구현체 (MyBatis Mapper 활용)
 */
@Repository
@RequiredArgsConstructor
public class MaterialRepositoryImpl implements MaterialRepository {

    private final MaterialMapper materialMapper;

    @Override
    public Material save(Material material) {
        return materialMapper.save(material);
    }

    @Override
    public Optional<Material> findById(int id) {
        return materialMapper.findById(id);
    }

    @Override
    public Optional<Material> findByIdWithBom(int id) {
        return materialMapper.findByIdWithBom(id);
    }

    @Override
    public boolean existsByMaterialCode(String materialCode) {
        return materialMapper.existsByMaterialCode(materialCode);
    }
}
