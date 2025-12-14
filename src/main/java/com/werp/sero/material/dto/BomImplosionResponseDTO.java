package com.werp.sero.material.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * BOM 역전개 응답 DTO
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BomImplosionResponseDTO {

    private MaterialInfo rawMaterial;
    private List<UsedInProduct> usedInProducts;
    private int totalProductCount;

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
        private Long unitPrice;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class UsedInProduct {
        private int productId;
        private String productName;
        private String productCode;
        private String productSpec;
        private String baseUnit;
        private int requirement;          // 완제품 1개당 필요한 원자재 수량
        private Long productUnitPrice;    // 완제품 단가
    }
}
