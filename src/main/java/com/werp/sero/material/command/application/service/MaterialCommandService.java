package com.werp.sero.material.command.application.service;

import com.werp.sero.material.command.application.dto.MaterialCreateRequestDTO;
import com.werp.sero.material.command.application.dto.MaterialCreateResponseDTO;
import com.werp.sero.material.command.application.dto.MaterialUpdateRequestDTO;

/**
 * 자재 Command Service 인터페이스
 */
public interface MaterialCommandService {

    /**
     * 자재 등록
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
}
