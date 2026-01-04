package com.werp.sero.production.query.dto.dashboard;

import java.util.List;

public record ProductionMonthlyTrendResponseDTO(
        List<String> labels,   // yyyy-MM
        List<Integer> target,  // 월별 목표 수량
        List<Integer> actual   // 월별 실생산 수량
) {
}
