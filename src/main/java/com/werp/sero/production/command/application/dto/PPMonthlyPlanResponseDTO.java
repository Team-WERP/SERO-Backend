package com.werp.sero.production.command.application.dto;

import lombok.Getter;

@Getter
public class PPMonthlyPlanResponseDTO {
    private int ppId;
    private int productionLineId;
    private String itemName;
    private String startDate;
    private String endDate;
    private Integer productionQuantity;
    private String status;
}
