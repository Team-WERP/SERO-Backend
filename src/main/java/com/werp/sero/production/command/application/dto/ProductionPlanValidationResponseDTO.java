package com.werp.sero.production.command.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductionPlanValidationResponseDTO {
    private boolean valid;
    private String message;

    public static ProductionPlanValidationResponseDTO ok() {
        return new ProductionPlanValidationResponseDTO(
                true,
                "생산계획 수립이 가능합니다."
        );
    }

    public static ProductionPlanValidationResponseDTO fail(String message) {
        return new ProductionPlanValidationResponseDTO(false, message);
    }
}
