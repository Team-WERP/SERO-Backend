package com.werp.sero.production.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WorkOrderResultResponse {
    private String woCode;

    private int goodQuantity;
    private int defectiveQuantity;

    private String startTime;
    private String endTime;
    private int workTime;   // 분

    private String note;
}
