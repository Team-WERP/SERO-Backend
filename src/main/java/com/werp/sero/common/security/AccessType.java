package com.werp.sero.common.security;


/**
 * 권한 접근 타입
 */
public enum AccessType {
    /**
     * 읽기 권한 (조회)
     */
    READ,

    /**
     * 쓰기 권한 (등록, 수정, 삭제)
     */
    WRITE
}