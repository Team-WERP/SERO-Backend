package com.werp.sero.production.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PPDailyPreviewResponseDTO {

    // PP
    private int ppId;
    private String ppCode;

    // PR
    private int prId;
    private String prCode;

    // Line
    private int lineId;
    private String lineName;

    // Material
    private int materialId;
    private String materialCode;
    private String materialName;

    // Period
    private String startDate;
    private String endDate;

    // Quantity
    private int plannedQuantity;
    private int todayWorkQuantity;
    private int totalProducedQty;
    private int todayProducedQty;
    private int remainingQuantity;
    private int progressRate;
}
