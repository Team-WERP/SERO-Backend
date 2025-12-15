package com.werp.sero.material.dto;

import com.werp.sero.material.entity.Bom;
import com.werp.sero.material.entity.Material;
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

    public static MaterialDetailResponseDTO from(Material material) {
        return MaterialDetailResponseDTO.builder()
                .id(material.getId())
                .name(material.getName())
                .materialCode(material.getMaterialCode())
                .spec(material.getSpec())
                .operationUnit(material.getOperationUnit())
                .baseUnit(material.getBaseUnit())
                .moq(material.getMoq())
                .cycleTime(material.getCycleTime())
                .unitPrice(material.getUnitPrice())
                .imageUrl(material.getImageUrl())
                .conversionRate(material.getConversionRate())
                .safetyStock(material.getSafetyStock())
                .rawMaterialCount(material.getRawMaterialCount())
                .type(material.getType())
                .status(material.getStatus())
                .createdAt(material.getCreatedAt())
                .updatedAt(material.getUpdatedAt())
                .manager(ManagerDTO.from(material.getEmployee()))
                .bomList(material.getBomList().stream()
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

        public static ManagerDTO from(com.werp.sero.employee.entity.Employee employee) {
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

        public static BomDTO from(Bom bom) {
            return BomDTO.builder()
                    .id(bom.getId())
                    .requirement(bom.getRequirement())
                    .note(bom.getNote())
                    .createdAt(bom.getCreatedAt())
                    .updatedAt(bom.getUpdatedAt())
                    .rawMaterial(RawMaterialDTO.from(bom.getRawMaterial()))
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

        public static RawMaterialDTO from(Material material) {
            if (material == null) return null;
            return RawMaterialDTO.builder()
                    .id(material.getId())
                    .name(material.getName())
                    .materialCode(material.getMaterialCode())
                    .spec(material.getSpec())
                    .baseUnit(material.getBaseUnit())
                    .unitPrice(material.getUnitPrice())
                    .build();
        }
    }
}
