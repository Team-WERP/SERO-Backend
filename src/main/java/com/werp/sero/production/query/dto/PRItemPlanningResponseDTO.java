package com.werp.sero.production.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
public class PRItemPlanningResponseDTO {
    private int prItemId;

    private String itemCode;
    private String itemName;

    private int requestedQuantity;
    private int plannedQuantity;
    private int remainingQuantity;

    private List<ProductionPlanSummaryDTO> plans;
}
