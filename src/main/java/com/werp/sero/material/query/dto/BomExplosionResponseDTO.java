package com.werp.sero.material.query.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * BOM 정전개 응답 DTO
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BomExplosionResponseDTO {

    private MaterialInfo finishedGood;
    private int requestedQuantity;
    private List<RequiredMaterial> requiredMaterials;
    private int totalMaterialCount;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class MaterialInfo {
        private int id;
        private String name;
        private String materialCode;
        private String spec;
        private String type;
        private String baseUnit;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class RequiredMaterial {
        private int materialId;
        private String materialName;
        private String materialCode;
        private String spec;
        private String baseUnit;
        private int unitRequirement;      // 완제품 1개당 필요 수량
        private int totalRequirement;     // 총 필요 수량 (요청 수량 × 단위 소요량)
        private Long unitPrice;           // 단가
        private Long totalPrice;          // 총 금액
    }
}
