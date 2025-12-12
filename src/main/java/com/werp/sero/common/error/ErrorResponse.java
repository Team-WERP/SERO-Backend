package com.werp.sero.common.error;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ErrorResponse {
    private String code;
    private String message;

    public ErrorResponse(final String code, final String message) {
        this.code = code;
        this.message = message;
    }

    public static ErrorResponse of(final String code, final String message) {
        return new ErrorResponse(code, message);
    }
}