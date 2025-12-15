package com.werp.sero.material.dto;

import com.werp.sero.material.entity.Material;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 자재 목록 조회 응답 DTO
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MaterialListResponseDTO {

    private int id;
    private String name;
    private String materialCode;
    private String spec;
    private String type;
    private String status;
    private String baseUnit;
    private Long unitPrice;
    private int safetyStock;
    private Integer rawMaterialCount;
    private String createdAt;

    public static MaterialListResponseDTO from(Material material) {
        return MaterialListResponseDTO.builder()
                .id(material.getId())
                .name(material.getName())
                .materialCode(material.getMaterialCode())
                .spec(material.getSpec())
                .type(material.getType())
                .status(material.getStatus())
                .baseUnit(material.getBaseUnit())
                .unitPrice(material.getUnitPrice())
                .safetyStock(material.getSafetyStock())
                .rawMaterialCount(material.getRawMaterialCount())
                .createdAt(material.getCreatedAt())
                .build();
    }
}
