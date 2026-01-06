package com.werp.sero.production.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WorkOrderResultListResponseDTO {

    private int woId;
    private String woCode;

    private int lineId;
    private String lineName;

    private String itemName;          // 대표 품목명

    private int plannedQuantity;
    private int producedQuantity;
    private int defectiveQuantity;

    private String startTime;
    private String endTime;
    private int workMinutes;

    private String workerName;
}

