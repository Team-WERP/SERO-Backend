package com.werp.sero.common.security.exception;

/**
 * 권한 거부 예외
 */
public class PermissionDeniedException extends RuntimeException {
    public PermissionDeniedException(String message) {
        super(message);
    }
}
