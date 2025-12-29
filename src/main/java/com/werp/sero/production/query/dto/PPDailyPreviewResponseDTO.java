package com.werp.sero.production.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PPDailyPreviewResponseDTO {

    // PP
    private int ppId;
    private String ppCode;

    // Line
    private int lineId;
    private String lineName;
    private int dailyCapacity;

    // Material
    private int materialId;
    private String materialCode;
    private String materialName;
    private String baseUnit;

    // Quantity
    private int dailyPlannedQuantity; // 오늘 계획 수량
    private int woPlannedQuantity;    // 작업지시 확정 수량

    // Flag
    private boolean hasWorkOrder;
}
