package com.werp.sero.common.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    /* COMMON */
    ENTITY_NOT_FOUND(HttpStatus.NOT_FOUND, "COMMON001", "Entity not found"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON002", "Internal server error"),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "COMMON003", "Bad request"),

    /* SALES ORDER */
    SALES_ORDER_LIST_NOT_FOUND(HttpStatus.NOT_FOUND, "ORDER001", "주문 목록을 찾을 수 없습니다."),
    SALES_ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, "ORDER002", "주문 정보를 찾을 수 없습니다."),

    /* Client */
    CLIENT_ITEM_NOT_FOUND(HttpStatus.NOT_FOUND, "CLIENT001", "해당 고객사의 거래 품목이 아닙니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

    ErrorCode(final HttpStatus status, final String code, final String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}