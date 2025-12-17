package com.werp.sero.material.query.service;

import com.werp.sero.material.query.dto.MaterialDetailResponseDTO;
import com.werp.sero.material.query.dto.MaterialListResponseDTO;

import java.util.List;

/**
 * 자재 Query Service 인터페이스 (조회 전용)
 */
public interface MaterialQueryService {

    /**
     * 자재 목록 조회
     */
    List<MaterialListResponseDTO> getMaterialList(
            String type,
            String status,
            String keyword
    );

    /**
     * 자재 상세 조회
     */
    MaterialDetailResponseDTO getMaterialDetail(int materialId);
}
