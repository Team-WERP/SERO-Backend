package com.werp.sero.material.query.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 자재 상세 조회 응답 DTO
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MaterialDetailResponseDTO {

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
    private List<BomDTO> bomList;

    public static MaterialDetailResponseDTO from(MaterialWithBomResponseDTO materialWithBom) {
        return MaterialDetailResponseDTO.builder()
                .id(materialWithBom.getId())
                .name(materialWithBom.getName())
                .materialCode(materialWithBom.getMaterialCode())
                .spec(materialWithBom.getSpec())
                .operationUnit(materialWithBom.getOperationUnit())
                .baseUnit(materialWithBom.getBaseUnit())
                .moq(materialWithBom.getMoq())
                .cycleTime(materialWithBom.getCycleTime())
                .unitPrice(materialWithBom.getUnitPrice())
                .imageUrl(materialWithBom.getImageUrl())
                .conversionRate(materialWithBom.getConversionRate())
                .safetyStock(materialWithBom.getSafetyStock())
                .rawMaterialCount(materialWithBom.getRawMaterialCount())
                .type(materialWithBom.getType())
                .status(materialWithBom.getStatus())
                .createdAt(materialWithBom.getCreatedAt())
                .updatedAt(materialWithBom.getUpdatedAt())
                .manager(ManagerDTO.from(materialWithBom.getEmployee()))
                .bomList(materialWithBom.getBomList().stream()
                        .map(BomDTO::from)
                        .toList())
                .build();
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ManagerDTO {
        private int id;
        private String name;
        private String empCode;

        public static ManagerDTO from(com.werp.sero.employee.command.domain.aggregate.Employee employee) {
            if (employee == null) return null;
            return ManagerDTO.builder()
                    .id(employee.getId())
                    .name(employee.getName())
                    .empCode(employee.getEmpCode())
                    .build();
        }
    }

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

        public static BomDTO from(MaterialWithBomResponseDTO.BomDTO bomDto) {
            return BomDTO.builder()
                    .id(bomDto.getId())
                    .requirement(bomDto.getRequirement())
                    .note(bomDto.getNote())
                    .createdAt(bomDto.getCreatedAt())
                    .updatedAt(bomDto.getUpdatedAt())
                    .rawMaterial(RawMaterialDTO.from(bomDto.getRawMaterial()))
                    .build();
        }
    }

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

        public static RawMaterialDTO from(MaterialWithBomResponseDTO.RawMaterialDTO rawMaterialDto) {
            if (rawMaterialDto == null) return null;
            return RawMaterialDTO.builder()
                    .id(rawMaterialDto.getId())
                    .name(rawMaterialDto.getName())
                    .materialCode(rawMaterialDto.getMaterialCode())
                    .spec(rawMaterialDto.getSpec())
                    .baseUnit(rawMaterialDto.getBaseUnit())
                    .unitPrice(rawMaterialDto.getUnitPrice())
                    .build();
        }
    }
}
