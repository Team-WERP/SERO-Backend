package com.werp.sero.production.command.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WorkOrderMaterialPreviewDTO {

    private int materialId;
    private String materialName;
    private String materialCode;
    private String spec;
    private String unit;

    private int plannedQuantity;
    private int expectedQuantity;
    private int actualQuantity;
}
