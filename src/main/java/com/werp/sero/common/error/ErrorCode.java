package com.werp.sero.common.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    /* COMMON */
    ENTITY_NOT_FOUND(HttpStatus.NOT_FOUND, "COMMON001", "Entity not found"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON002", "Internal server error"),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "COMMON003", "Bad request"),

    /* System */
    SYSTEM_COMMON_CODE_NOT_FOUND(HttpStatus.NOT_FOUND, "SYSTEM001", "공통코드를 찾을 수 없습니다."),

    /* Client */
    CLIENT_ITEM_NOT_FOUND(HttpStatus.NOT_FOUND, "CLIENT001", "해당 고객사의 거래 품목이 아닙니다."),
    CLIENT_NOT_FOUND(HttpStatus.NOT_FOUND, "CLIENT002", "고객사를 찾을 수 없습니다."),
    CLIENT_ADDRESS_NOT_FOUND(HttpStatus.NOT_FOUND, "CLIENT003", "배송지를 찾을 수 없습니다.");


    private final HttpStatus status;
    private final String code;
    private final String message;

    ErrorCode(final HttpStatus status, final String code, final String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}