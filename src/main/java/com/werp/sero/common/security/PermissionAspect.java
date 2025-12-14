package com.werp.sero.common.security;

import com.werp.sero.permission.entity.MenuPermission;
import com.werp.sero.permission.repository.MenuPermissionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

/**
 * @RequirePermission 어노테이션이 적용된 메서드의 권한을 검증하는 AOP
 *
 * ===== 중요: Spring Security 통합 가이드 =====
 *
 * 현재 상태: Spring Security가 아직 추가되지 않아 권한 체크가 비활성화되어 있습니다.
 *
 * Spring Security 추가 후 활성화 방법:
 * 1. build.gradle에 Spring Security 의존성 추가
 *    implementation 'org.springframework.boot:spring-boot-starter-security'
 *
 * 2. 이 파일에서 다음 작업 수행:
 *    - SECURITY_ENABLED 상수를 true로 변경
 *    - 주석 처리된 import 문들 활성화 (Authentication, SecurityContextHolder 등)
 *    - checkPermission() 메서드의 실제 구현부 주석 해제
 *
 * JWT 토큰 구조:
 * - auth 필드에 쉼표로 구분된 권한 코드 저장 (예: "AC_SYS,AC_SAL")
 * - 사용자는 여러 권한을 동시에 가질 수 있음
 * - 권한 중 하나라도 매칭되면 접근 허용
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class PermissionAspect {

    private final MenuPermissionRepository menuPermissionRepository;

    // TODO: Spring Security 추가 후 true로 변경
    private static final boolean SECURITY_ENABLED = false;

    @Before("@annotation(com.werp.sero.common.security.RequirePermission)")
    public void checkPermission(JoinPoint joinPoint) {
        if (!SECURITY_ENABLED) {
            // Spring Security가 추가되기 전까지는 권한 체크를 건너뜀
            log.debug("권한 체크 비활성화 상태 (Spring Security 미설치)");
            return;
        }

        // ===== 아래 코드는 Spring Security 추가 후 활성화하세요 =====
        /*
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        RequirePermission requirePermission = method.getAnnotation(RequirePermission.class);

        // Spring Security Context에서 인증 정보 가져오기
        // import org.springframework.security.core.Authentication;
        // import org.springframework.security.core.context.SecurityContextHolder;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 인증되지 않은 경우
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UnauthorizedException("인증되지 않은 사용자입니다.");
        }

        // 사용자의 권한 코드 목록 추출 (JWT의 auth 필드: "AC_SYS,AC_SAL")
        List<String> userAuthorityCodes = extractAuthorityCodes(authentication);

        // 메뉴 코드
        String menuCode = requirePermission.menu();

        // 허용된 권한 목록
        String[] allowedAuthorities = requirePermission.authorities();

        // 접근 타입
        AccessType accessType = requirePermission.accessType();

        // 빈 배열인 경우 모든 권한 허용
        if (allowedAuthorities.length > 0) {
            // 사용자가 허용된 권한 중 하나라도 가지고 있는지 확인
            boolean hasAuthority = userAuthorityCodes.stream()
                .anyMatch(userAuth -> Arrays.asList(allowedAuthorities).contains(userAuth));

            if (!hasAuthority) {
                throw new PermissionDeniedException(
                    String.format("해당 기능에 접근할 권한이 없습니다. (메뉴: %s, 필요 권한: %s, 보유 권한: %s)",
                        menuCode, String.join(", ", allowedAuthorities), String.join(", ", userAuthorityCodes))
                );
            }
        }

        // 사용자가 가진 권한 중 하나라도 해당 메뉴에 대한 권한이 있는지 확인
        boolean hasMenuPermission = false;

        for (String userAuthorityCode : userAuthorityCodes) {
            List<MenuPermission> menuPermissions = menuPermissionRepository
                .findByMenuCodeAndPermissionCode(menuCode, userAuthorityCode);

            if (!menuPermissions.isEmpty()) {
                MenuPermission menuPermission = menuPermissions.get(0);

                // 접근 타입에 따른 권한 확인
                if (accessType == AccessType.READ && menuPermission.isReadPermission()) {
                    hasMenuPermission = true;
                    break;
                } else if (accessType == AccessType.WRITE && menuPermission.isWritePermission()) {
                    hasMenuPermission = true;
                    break;
                }
            }
        }

        if (!hasMenuPermission) {
            throw new PermissionDeniedException(
                String.format("해당 메뉴에 %s 권한이 없습니다. (메뉴: %s, 보유 권한: %s)",
                    accessType == AccessType.READ ? "읽기" : "쓰기",
                    menuCode,
                    String.join(", ", userAuthorityCodes))
            );
        }
        */
    }

    /**
     * Spring Security Authentication에서 권한 코드 목록 추출
     * JWT의 auth 필드 값: "AC_SYS,AC_SAL" 형태를 파싱
     *
     * Spring Security 추가 후 이 메서드의 주석을 해제하세요
     */
    /*
    private List<String> extractAuthorityCodes(Authentication authentication) {
        return authentication.getAuthorities().stream()
            .flatMap(grantedAuthority -> {
                String authority = grantedAuthority.getAuthority();

                // ROLE_ 접두사 제거
                if (authority.startsWith("ROLE_")) {
                    authority = authority.substring(5);
                }

                // 쉼표로 구분된 권한을 분리 (예: "AC_SYS,AC_SAL" -> ["AC_SYS", "AC_SAL"])
                return Arrays.stream(authority.split(","))
                    .map(String::trim)
                    .filter(auth -> !auth.isEmpty());
            })
            .toList();
    }
    */
}
