package com.werp.sero.common.security;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 메서드 실행 시 필요한 권한을 지정하는 어노테이션
 *
 * 사용 예시:
 * @RequirePermission(menu = "MM_MAT", authorities = {"AC_SAL", "AC_PRO"}, accessType = AccessType.WRITE)
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequirePermission {

    /**
     * 메뉴 코드 (예: MM_MAT, MM_CORP, MM_EMP)
     */
    String menu();

    /**
     * 허용된 권한 코드 목록 (예: AC_SAL, AC_PRO, AC_WHS)
     * 빈 배열인 경우 모든 권한 허용
     */
    String[] authorities() default {};

    /**
     * 접근 타입 (READ: 조회, WRITE: 수정)
     */
    AccessType accessType() default AccessType.READ;
}