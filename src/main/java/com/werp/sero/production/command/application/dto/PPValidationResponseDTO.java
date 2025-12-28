package com.werp.sero.production.command.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class PPValidationResponseDTO {
    private boolean valid;
    private String message;

    private final String exceededDate;   // yyyy-MM-dd
    private final Integer dailyCapa;
    private final Integer plannedQty;

    private PPValidationResponseDTO(
            boolean valid,
            String message,
            String exceededDate,
            Integer dailyCapa,
            Integer plannedQty
    ) {
        this.valid = valid;
        this.message = message;
        this.exceededDate = exceededDate;
        this.dailyCapa = dailyCapa;
        this.plannedQty = plannedQty;
    }


    // 정상
    public static PPValidationResponseDTO ok() {
        return new PPValidationResponseDTO(
                true,
                "생산계획 수립이 가능합니다.",
                null,
                null,
                null
        );
    }

    // Capa 초과
    public static PPValidationResponseDTO capacityExceeded(
            String date,
            int dailyCapa,
            int plannedQty
    ) {
        return new PPValidationResponseDTO(
                false,
                date + " 기준 일일 생산량 " + plannedQty + "ea → Capa " + dailyCapa + "ea 초과",
                date,
                dailyCapa,
                plannedQty
        );
    }
}
