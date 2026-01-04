package com.werp.sero.production.query.dto.dashboard;

public record ProductionLineStatusResponseDTO(
        int running,   // 가동 중
        int paused,    // 일시 정지
        int stopped    // 비가동
) {
}
