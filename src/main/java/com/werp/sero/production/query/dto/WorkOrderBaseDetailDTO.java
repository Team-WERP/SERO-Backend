package com.werp.sero.production.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WorkOrderBaseDetailDTO {

    private int woId;
    private String woCode;
    private String status;

    private int lineId;
    private String lineName;

    private String managerName;

    private int plannedQuantity;
    private int goodQuantity;
    private int defectiveQuantity;
    private int workTime;

    private String startTime;
    private String endTime;
}

