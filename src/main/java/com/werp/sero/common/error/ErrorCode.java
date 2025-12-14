package com.werp.sero.common.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    /* COMMON */
    ENTITY_NOT_FOUND(HttpStatus.NOT_FOUND, "COMMON001", "Entity not found"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON002", "Internal server error"),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "COMMON003", "Bad request"),

    /* ORDER */
    ORDER_LIST_NOT_FOUND(HttpStatus.NOT_FOUND, "ORDER001", "Order list not found"),
    ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, "ORDER002", "Order not found"),

    /* EMPLOYEE */
    EMPLOYEE_LIST_NOT_FOUND(HttpStatus.NOT_FOUND, "ORDER001", "Order list not found");


    private final HttpStatus status;
    private final String code;
    private final String message;

    ErrorCode(final HttpStatus status, final String code, final String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}