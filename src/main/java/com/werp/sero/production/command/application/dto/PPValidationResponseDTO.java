package com.werp.sero.production.command.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PPValidationResponseDTO {
    private boolean valid;
    private String message;

    public static PPValidationResponseDTO ok() {
        return new PPValidationResponseDTO(
                true,
                "생산계획 수립이 가능합니다."
        );
    }
}
