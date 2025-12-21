package com.werp.sero.production.command.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductionPlanCreateResponseDTO {
    private int productionPlanId;
    private String ppCode;
}
