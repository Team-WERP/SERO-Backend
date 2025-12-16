package com.werp.sero.material.query.service;

import com.werp.sero.material.command.domain.aggregate.Material;
import com.werp.sero.material.exception.InvalidMaterialStatusException;
import com.werp.sero.material.exception.InvalidMaterialTypeException;
import com.werp.sero.material.exception.MaterialNotFoundException;
import com.werp.sero.material.query.dao.MaterialMapper;
import com.werp.sero.material.query.dto.MaterialDetailResponseDTO;
import com.werp.sero.material.query.dto.MaterialListResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * 자재 Query Service 구현체 (조회 전용)
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MaterialQueryServiceImpl implements MaterialQueryService {

    private final MaterialMapper materialMapper;

    // 허용되는 자재 유형
    private static final List<String> VALID_MATERIAL_TYPES = Arrays.asList("MAT_FG", "MAT_RM");

    // 허용되는 자재 상태
    private static final List<String> VALID_MATERIAL_STATUSES = Arrays.asList("MAT_NORMAL", "MAT_STOP");

    @Override
    public List<MaterialListResponseDTO> getMaterialList(
            String type, String status, String keyword) {

        // 1. 자재 유형 유효성 검증 (값이 있을 경우에만)
        if (type != null && !type.isEmpty() && !VALID_MATERIAL_TYPES.contains(type)) {
            throw new InvalidMaterialTypeException();
        }

        // 2. 자재 상태 유효성 검증 (값이 있을 경우에만)
        if (status != null && !status.isEmpty() && !VALID_MATERIAL_STATUSES.contains(status)) {
            throw new InvalidMaterialStatusException();
        }

        // 3. 자재 목록 조회
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
