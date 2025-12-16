package com.werp.sero.material.command.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 자재 등록 요청 DTO
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MaterialCreateRequestDTO {

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
    private Integer safetyStock;
    private String type;
    private String status;
    private List<BomRequest> bomList;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BomRequest {
        private int rawMaterialId;
        private int requirement;
        private String note;
    }
}
