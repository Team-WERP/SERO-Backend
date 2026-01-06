package com.werp.sero.production.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PPDailyPreviewResponseDTO {

    // Line
    private int lineId;
    private String lineName;
    private int dailyCapacity;

    // Production Plan
    private int ppId;
    private String ppCode;

    // Item (PR Item 기준)
    private int prItemId;
    private String itemCode;
    private String itemName;
    private String unit;

    // Quantity
    private int dailyPlannedQuantity;   // 오늘 계획 수량
    private int assignedWoQuantity;     // 오늘 배정된 작업지시량
}
