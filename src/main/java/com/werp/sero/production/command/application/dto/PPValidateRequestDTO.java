package com.werp.sero.production.command.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PPValidateRequestDTO {
    private int prItemId;
    private int productionLineId;

    private String startDate;
    private String endDate;

    private int productionQuantity;
}
