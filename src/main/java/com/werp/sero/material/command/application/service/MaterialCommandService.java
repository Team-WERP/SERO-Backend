package com.werp.sero.material.command.application.service;

import com.werp.sero.material.command.application.dto.BomCreateRequestDTO;
import com.werp.sero.material.command.application.dto.BomCreateResponseDTO;
import com.werp.sero.material.command.application.dto.MaterialCreateRequestDTO;
import com.werp.sero.material.command.application.dto.MaterialCreateResponseDTO;
import com.werp.sero.material.command.application.dto.MaterialUpdateRequestDTO;

/**
 * 자재 Command Service 인터페이스
 */
public interface MaterialCommandService {

    /**
     * 자재 등록 (BOM 제외)
     * @return 생성된 자재 정보
     */
    MaterialCreateResponseDTO createMaterial(MaterialCreateRequestDTO materialCreateRequestDTO, int loginEmployeeId);

    /**
     * 자재 수정
     */
    void updateMaterial(int materialId, MaterialUpdateRequestDTO materialUpdateRequestDTO);

    /**
     * 자재 비활성화
     */
    void deactivateMaterial(int materialId);

    /**
     * 자재 활성화
     */
    void activateMaterial(int materialId);

    /**
     * BOM 구성 추가
     * @param materialId 자재 ID
     * @param request BOM 구성 요청 정보
     * @return BOM 구성 정보
     */
    BomCreateResponseDTO addBom(int materialId, BomCreateRequestDTO request);

    /**
     * BOM 구성 수정 (전체 교체)
     * @param materialId 자재 ID
     * @param request BOM 구성 요청 정보
     * @return BOM 구성 정보
     */
    BomCreateResponseDTO updateBom(int materialId, BomCreateRequestDTO request);
}
