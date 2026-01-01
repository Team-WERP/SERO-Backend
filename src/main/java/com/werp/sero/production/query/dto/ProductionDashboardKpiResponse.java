package com.werp.sero.production.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductionDashboardKpiResponse {

    private String date;
    private TodayProduction today;
    private WorkOrderSummary workOrder;
    private int delayRiskCount;

    @Getter
    @AllArgsConstructor
    public static class TodayProduction {
        private int plannedQty;
        private int producedQty;
        private double achievementRate;
    }

    @Getter
    @AllArgsConstructor
    public static class WorkOrderSummary {
        private int total;
        private int running;
        private int paused;
        private int done;
    }
}

