package com.werp.sero.material.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * BOM 정전개 요청 DTO
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BomExplosionRequestDTO {

    private int materialId;  // 완제품 ID
    private int quantity;    // 생산 수량
}
