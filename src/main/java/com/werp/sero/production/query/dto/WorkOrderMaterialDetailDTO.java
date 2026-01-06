package com.werp.sero.production.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WorkOrderMaterialDetailDTO {

    private int materialId;
    private String materialName;

    private int plannedQuantity;
    private int actualQuantity;

    private String status;
}

