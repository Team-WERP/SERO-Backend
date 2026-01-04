package com.werp.sero.production.query.dto.dashboard;

public record ProductionDashboardSummaryResponseDTO(
        double achievementRate,     // 금일 생산 달성률(%)
        int completedQty,           // 양품 수량
        int targetQty,              // 목표 수량
        double defectRate,          // 공정 불량률(%)
        double lineUtilizationRate  // 라인 가동률(%)
) { }
