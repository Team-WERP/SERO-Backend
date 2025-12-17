package com.werp.sero.common.error;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor
@Getter
@Schema(description = "에러 응답")
public class ErrorResponse {

    @Schema(description = "에러 코드")
    private String code;

    @Schema(description = "에러 메시지")
    private String message;

    @Schema(description = "발생 시각")
    private String timestamp;

    public ErrorResponse(final String code, final String message) {
        this.code = code;
        this.message = message;
        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public static ErrorResponse of(final String code, final String message) {
        return new ErrorResponse(code, message);
    }
}