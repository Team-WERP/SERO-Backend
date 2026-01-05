package com.werp.sero.production.query.dto.dashboard;

public record ProductionLineCapaListDTO(
        int lineId,
        String lineName,
        int dailyCapacity,
        int plannedLoad
) {
}
