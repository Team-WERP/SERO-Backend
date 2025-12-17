package com.werp.sero.common.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    /* COMMON */
    ENTITY_NOT_FOUND(HttpStatus.NOT_FOUND, "COMMON001", "Entity not found"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON002", "Internal server error"),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "COMMON003", "Bad request"),

    /* EMPLOYEE */
    EMPLOYEE_NOT_FOUND(HttpStatus.NOT_FOUND, "EMP001", "Employee not found"),

    /* CLIENT EMPLOYEE */
    CLIENT_EMPLOYEE_NOT_FOUND(HttpStatus.NOT_FOUND, "CEMP001", "Client employee not found"),

    /* AUTH */
    LOGIN_FAILED(HttpStatus.UNAUTHORIZED, "AUTH001", "로그인에 실패했습니다."),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH002", "만료된 토큰입니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH003", "유효하지 않은 토큰입니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "AUTH004", "인증이 필요합니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN, "AUTH005", "접근 권한이 없습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

    ErrorCode(final HttpStatus status, final String code, final String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}