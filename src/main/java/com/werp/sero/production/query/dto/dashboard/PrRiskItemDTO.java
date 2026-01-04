package com.werp.sero.production.query.dto.dashboard;

public record PrRiskItemDTO(
        int itemId,
        String itemName,
        String lineName,

        int targetQty,
        int producedQty,
        int progressRate,     // %

        String materialStatus, // NORMAL / SHORTAGE
        boolean capaOverload,

        int riskScore
) {
}
