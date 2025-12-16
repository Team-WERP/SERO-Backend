package com.werp.sero.common.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    /* COMMON */
    ENTITY_NOT_FOUND(HttpStatus.NOT_FOUND, "COMMON001", "Entity not found"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON002", "Internal server error"),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "COMMON003", "Bad request"),

    /* MATERIAL */
    MATERIAL_NOT_FOUND(HttpStatus.NOT_FOUND, "MATERIAL001", "Material not found"),
    MATERIAL_CODE_DUPLICATED(HttpStatus.CONFLICT, "MATERIAL002", "Material code already exists"),

    /* WAREHOUSE */
    WAREHOUSE_STOCK_NOT_FOUND(HttpStatus.NOT_FOUND, "WAREHOUSE001", "Warehouse stock not found"),

    /* CLIENT */
    CLIENT_NOT_FOUND(HttpStatus.NOT_FOUND, "CLIENT001", "Client not found"),
    CLIENT_BUSINESS_NO_DUPLICATED(HttpStatus.CONFLICT, "CLIENT002", "Business number already exists");

    private final HttpStatus status;
    private final String code;
    private final String message;

    ErrorCode(final HttpStatus status, final String code, final String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}