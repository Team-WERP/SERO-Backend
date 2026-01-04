package com.werp.sero.production.query.dto.dashboard;

public record PrRiskItemRawDTO(
        int prItemId,
        int prId,

        String itemName,

        int targetQty,        // 요청 수량
        int producedQty,      // 현재 실적

        String lineName,      // 배정 라인
        int materialId,       // 자재 부족 여부 판단용

        int totalDays,        // (dueDate - startDate + 1)
        int elapsedDays       // (today - startDate + 1)
) {
}
