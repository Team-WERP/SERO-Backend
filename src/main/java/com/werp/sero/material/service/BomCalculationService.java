package com.werp.sero.material.service;

import com.werp.sero.material.dto.BomExplosionRequestDTO;
import com.werp.sero.material.dto.BomExplosionResponseDTO;
import com.werp.sero.material.dto.BomImplosionResponseDTO;
import com.werp.sero.material.dto.MaterialSearchResponseDTO;

import java.util.List;

/**
 * BOM 소요량 계산 서비스
 */
public interface BomCalculationService {

    /**
     * 자재 검색
     *
     * @param keyword 검색어
     * @param type 자재 타입
     * @return 검색된 자재 목록
     */
    List<MaterialSearchResponseDTO> searchMaterials(String keyword, String type);

    /**
     * 정전개: 완제품 생산에 필요한 원부자재 소요량 계산
     *
     * @param request 완제품 ID와 생산 수량
     * @return 필요한 원부자재 목록 및 수량
     */
    BomExplosionResponseDTO calculateExplosion(BomExplosionRequestDTO request);

    /**
     * 역전개: 특정 원부자재를 사용하는 완제품 목록 조회
     *
     * @param rawMaterialId 원부자재 ID
     * @return 해당 원자재를 사용하는 완제품 목록
     */
    BomImplosionResponseDTO calculateImplosion(int rawMaterialId);
}
