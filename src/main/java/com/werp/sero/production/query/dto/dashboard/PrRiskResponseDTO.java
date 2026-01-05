package com.werp.sero.production.query.dto.dashboard;

import java.util.List;

public record PrRiskResponseDTO(
        int prId,
        String prCode,
        String clientName,
        String dueDate,
        int dDay,

        boolean isDelayExpected,

        List<PrRiskItemDTO> items,
        PrRiskSummaryDTO summary
) {
}
