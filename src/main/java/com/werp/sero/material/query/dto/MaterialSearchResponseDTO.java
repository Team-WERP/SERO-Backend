package com.werp.sero.material.query.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * BOM 계산용 품목 검색 응답 DTO
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MaterialSearchResponseDTO {

    private int id;
    private String name;
    private String materialCode;
    private String spec;
    private String type;
    private String baseUnit;
    private Integer rawMaterialCount;
}
