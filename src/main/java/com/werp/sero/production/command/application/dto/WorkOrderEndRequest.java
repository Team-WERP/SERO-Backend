package com.werp.sero.production.command.application.dto;

import lombok.Getter;

@Getter
public class WorkOrderEndRequest {
    private int goodQuantity;
    private int defectiveQuantity;

    private String startTime; // yyyy-MM-dd HH:mm:ss
    private String endTime;

    private String note;
}
