package com.werp.sero.production.command.application.dto;

import lombok.Getter;

@Getter
public class PPMonthlyPlanResponseDTO {
    private int ppId;
    private String ppCode;
    private int productionLineId;
    private int prId;
    private String prCode;
    private int prItemId;
    private int plannedQuantity;
    private String materialCode;
    private String materialName;
    private String unit;
    private String startDate;
    private String endDate;
    private Integer productionQuantity;
    private String status;
}