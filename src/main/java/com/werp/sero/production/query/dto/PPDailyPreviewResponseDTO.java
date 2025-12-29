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

    // Material
    private int materialId;
    private String materialCode;
    private String materialName;

    // Quantity
    private int plannedQuantity;      // PP 총 수량
    private int remainingQuantity;    // 잔여 수량
    private int recommendedQuantity;  // 오늘 권장 수량

    // Flag
    private boolean hasWorkOrder;     // 오늘 해당 라인에 WO 존재 여부
}
