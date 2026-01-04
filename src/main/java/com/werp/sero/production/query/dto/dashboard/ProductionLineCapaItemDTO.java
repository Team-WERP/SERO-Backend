package com.werp.sero.production.query.dto.dashboard;

public record ProductionLineCapaItemDTO(
        int lineId,
        String lineName,
        int load,        // 계획 생산량 합
        int capacity,    // 일일 생산 능력
        int loadRate     // 부하율 (%)
) {
}
