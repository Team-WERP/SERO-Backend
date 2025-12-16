package com.werp.sero.material.command.infrastructure.service;

import com.werp.sero.material.command.domain.aggregate.Material;
import com.werp.sero.material.command.domain.repository.MaterialRepository;
import com.werp.sero.material.command.infrastructure.repository.JpaMaterialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 자재 Repository 구현체 (JPA 활용)
 */
@Repository
@RequiredArgsConstructor
public class MaterialRepositoryImpl implements MaterialRepository {

    private final JpaMaterialRepository jpaMaterialRepository;

    @Override
    public Material save(Material material) {
        return jpaMaterialRepository.save(material);
    }

    @Override
    public Optional<Material> findById(int id) {
        return jpaMaterialRepository.findById(id);
    }

    @Override
    public Optional<Material> findByIdWithBom(int id) {
        return jpaMaterialRepository.findByIdWithBom(id);
    }

    @Override
    public boolean existsByMaterialCode(String materialCode) {
        return jpaMaterialRepository.existsByMaterialCode(materialCode);
    }
}
