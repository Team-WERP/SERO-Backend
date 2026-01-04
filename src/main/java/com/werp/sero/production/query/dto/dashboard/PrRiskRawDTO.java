package com.werp.sero.production.query.dto.dashboard;

public record PrRiskRawDTO(
        int prId,
        String prCode,
        String clientName,
        String dueDate,     // yyyy-MM-dd
        int dDay            // D-Day
) {
}
