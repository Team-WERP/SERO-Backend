package com.werp.sero.production.query.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PRItemPlanningBaseDTO {
    private int prItemId;

    private String itemCode;
    private String itemName;

    private int requestedQuantity;
}
