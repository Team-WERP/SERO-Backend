package com.werp.sero.material.service;

import com.werp.sero.material.dto.MaterialCreateRequestDTO;
import com.werp.sero.material.dto.MaterialDetailResponseDTO;
import com.werp.sero.material.dto.MaterialListResponseDTO;
import com.werp.sero.material.dto.MaterialUpdateRequestDTO;

import java.util.List;

public interface MaterialService {

    /* 조회 */
    List<MaterialListResponseDTO> getMaterialList(
            String type,
            String status,
            String keyword
    );

    MaterialDetailResponseDTO getMaterialDetail(int materialId);

    void createMaterial(MaterialCreateRequestDTO materialCreateRequestDTO, int loginEmployeeId);

    void updateMaterial(int materialId, MaterialUpdateRequestDTO materialUpdateRequestDTO);

    void deactivateMaterial(int materialId);

}
