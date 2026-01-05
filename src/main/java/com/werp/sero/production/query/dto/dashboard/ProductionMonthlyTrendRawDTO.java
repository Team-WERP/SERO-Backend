package com.werp.sero.production.query.dto.dashboard;

public record ProductionMonthlyTrendRawDTO(
        String month,          // yyyy-MM
        int targetQuantity,    // 계획 수량
        int actualQuantity     // 실생산 수량
) {
}
