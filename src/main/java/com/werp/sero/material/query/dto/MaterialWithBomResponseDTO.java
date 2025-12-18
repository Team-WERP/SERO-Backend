package com.werp.sero.material.query.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * 자재 + BOM 조회 전용 DTO (MyBatis 매핑용)
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MaterialWithBomResponseDTO {

    private int id;
    private String name;
    private String materialCode;
    private String spec;
    private String operationUnit;
    private String baseUnit;
    private Integer moq;
    private Integer cycleTime;
    private Long unitPrice;
    private String imageUrl;
    private Integer conversionRate;
    private int safetyStock;
    private Integer rawMaterialCount;
    private String type;
    private String status;
    private String createdAt;
    private String updatedAt;
    private ManagerDTO manager;
    @Builder.Default
    private List<BomDTO> bomList = new ArrayList<>();

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ManagerDTO {
        private int id;
        private String name;
        private String empCode;
    }

    /**
     * BOM 조회 전용 DTO
     */
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class BomDTO {
        private int id;
        private int requirement;
        private String note;
        private String createdAt;
        private String updatedAt;
        private RawMaterialDTO rawMaterial;
    }

    /**
     * 원자재 조회 전용 DTO
     */
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class RawMaterialDTO {
        private int id;
        private String name;
        private String materialCode;
        private String spec;
        private String baseUnit;
        private Long unitPrice;
        private String type;
        private String status;
    }
}
