package com.werp.sero.common.security;

/**
 * 인증되지 않은 사용자 예외
 */
public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message) {
        super(message);
    }
}
