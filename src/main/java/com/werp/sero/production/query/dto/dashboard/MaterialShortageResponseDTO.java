package com.werp.sero.production.query.dto.dashboard;

public record MaterialShortageResponseDTO(
        int materialId,
        String materialCode,
        String materialName,
        int currentStock,
        int safetyStock,
        String warehouseName
) {
}
