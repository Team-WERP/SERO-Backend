package com.werp.sero.production.query.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductionPlanSummaryDTO {
    private int ppId;
    private int lineId;
    private String lineName;

    private String startDate;
    private String endDate;

    private int productionQuantity;
}
