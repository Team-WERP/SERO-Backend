package com.werp.sero.material.query.service;

import com.werp.sero.material.exception.InvalidMaterialStatusException;
import com.werp.sero.material.exception.InvalidMaterialTypeException;
import com.werp.sero.material.exception.MaterialNotFoundException;
import com.werp.sero.material.query.dao.MaterialMapper;
import com.werp.sero.material.query.dto.MaterialDetailResponseDTO;
import com.werp.sero.material.query.dto.MaterialListResponseDTO;
import com.werp.sero.material.query.dto.MaterialWithBomResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * 자재 Query Service 구현체 (조회 전용)
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MaterialQueryServiceImpl implements MaterialQueryService {

    private final MaterialMapper materialMapper;

    // 허용되는 자재 유형
    private static final List<String> VALID_MATERIAL_TYPES = Arrays.asList("MAT_FG", "MAT_RM");

    // 허용되는 자재 상태
    private static final List<String> VALID_MATERIAL_STATUSES = Arrays.asList("MAT_NORMAL", "MAT_STOP_PREP", "MAT_STOP", "MAT_DISCONTINUED");

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
        List<MaterialListResponseDTO> results = materialMapper.findByCondition(type, status, keyword);

        // DEBUG: imageUrl 확인
        if (!results.isEmpty()) {
            log.info("===== DEBUG: Material imageUrl check =====");
            log.info("First material ID: {}", results.get(0).getId());
            log.info("First material name: {}", results.get(0).getName());
            log.info("First material imageUrl: {}", results.get(0).getImageUrl());
            log.info("==========================================");
        }

        return results;
    }

    @Override
    public MaterialDetailResponseDTO getMaterialDetail(int materialId) {

        MaterialWithBomResponseDTO materialWithBom =
                materialMapper.findByIdWithBom(materialId)
                        .orElseThrow(MaterialNotFoundException::new);

        return MaterialDetailResponseDTO.from(materialWithBom);
    }
}
