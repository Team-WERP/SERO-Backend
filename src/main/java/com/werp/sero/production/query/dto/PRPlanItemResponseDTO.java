package com.werp.sero.production.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PRPlanItemResponseDTO {
    private int prItemId;
    private int soItemId;

    private String itemCode;
    private String itemName;

    private int requestedQuantity;
    private int plannedQuantity;
    private int remainingQuantity;

    private String status;
}
