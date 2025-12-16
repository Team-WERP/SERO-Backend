package com.werp.sero.material.query.service;

import com.werp.sero.material.command.domain.aggregate.Material;
import com.werp.sero.material.exception.MaterialNotFoundException;
import com.werp.sero.material.query.dao.MaterialMapper;
import com.werp.sero.material.query.dto.MaterialDetailResponseDTO;
import com.werp.sero.material.query.dto.MaterialListResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 자재 Query Service 구현체 (조회 전용)
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MaterialQueryServiceImpl implements MaterialQueryService {

    private final MaterialMapper materialMapper;

    @Override
    public List<MaterialListResponseDTO> getMaterialList(
            String type, String status, String keyword) {

        List<Material> materials =
                materialMapper.findByCondition(type, status, keyword);

        return materials.stream()
                .map(MaterialListResponseDTO::from)
                .toList();
    }

    @Override
    public MaterialDetailResponseDTO getMaterialDetail(int materialId) {

        Material material = materialMapper.findByIdWithBom(materialId)
                .orElseThrow(MaterialNotFoundException::new);

        return MaterialDetailResponseDTO.from(material);
    }
}
