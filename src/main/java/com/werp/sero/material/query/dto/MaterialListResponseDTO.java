package com.werp.sero.material.query.dto;

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
    private Integer cycleTime;
    private Integer rawMaterialCount;
    private String imageUrl;
    private String createdAt;
}
