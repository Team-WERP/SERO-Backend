package com.werp.sero.production.command.application.dto;

import lombok.Getter;

@Getter
public class ProductionPlanValidateRequestDTO {
    private int prItemId;
    private int productionLineId;

    private String startDate;
    private String endDate;

    private int productionQuantity;
}
