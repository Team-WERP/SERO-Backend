package com.werp.sero.common.security;

import com.werp.sero.permission.repository.MenuPermissionRepository;
import org.aspectj.lang.JoinPoint;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.Mockito.mock;

/**
 * PermissionAspect 테스트
 *
 * 참고: 현재 SECURITY_ENABLED = false 상태이므로 권한 체크가 비활성화되어 있습니다.
 * Spring Security가 추가되고 SECURITY_ENABLED를 true로 변경하면
 * 실제 권한 체크 로직이 동작합니다.
 *
 * 이 테스트는 Spring Security 통합 후 권한 체크 로직이 올바르게 작동하는지
 * 검증하기 위한 준비 테스트입니다.
 */
@DisplayName("PermissionAspect 테스트 (Spring Security 통합 전)")
class PermissionAspectTest {

    @Test
    @DisplayName("권한 체크 비활성화 상태 - 모든 요청 허용")
    void checkPermission_SecurityDisabled_AllowsAllRequests() throws NoSuchMethodException {
        // given
        MenuPermissionRepository mockRepository = mock(MenuPermissionRepository.class);
        PermissionAspect permissionAspect = new PermissionAspect(mockRepository);
        JoinPoint joinPoint = createMockJoinPoint(TestController.class.getDeclaredMethod("testMethod"));

        // when & then - 예외가 발생하지 않아야 함 (SECURITY_ENABLED = false이므로)
        assertThatCode(() -> permissionAspect.checkPermission(joinPoint))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("권한 체크 비활성화 상태 - READ 권한 요청 허용")
    void checkPermission_SecurityDisabled_AllowsReadPermission() throws NoSuchMethodException {
        // given
        MenuPermissionRepository mockRepository = mock(MenuPermissionRepository.class);
        PermissionAspect permissionAspect = new PermissionAspect(mockRepository);
        JoinPoint joinPoint = createMockJoinPoint(TestController.class.getDeclaredMethod("testReadMethod"));

        // when & then
        assertThatCode(() -> permissionAspect.checkPermission(joinPoint))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("권한 체크 비활성화 상태 - WRITE 권한 요청 허용")
    void checkPermission_SecurityDisabled_AllowsWritePermission() throws NoSuchMethodException {
        // given
        MenuPermissionRepository mockRepository = mock(MenuPermissionRepository.class);
        PermissionAspect permissionAspect = new PermissionAspect(mockRepository);
        JoinPoint joinPoint = createMockJoinPoint(TestController.class.getDeclaredMethod("testWriteMethod"));

        // when & then
        assertThatCode(() -> permissionAspect.checkPermission(joinPoint))
                .doesNotThrowAnyException();
    }

    // JoinPoint Mock 객체 생성 (실제로는 사용되지 않음 - SECURITY_ENABLED = false)
    private JoinPoint createMockJoinPoint(Method method) {
        return mock(JoinPoint.class);
    }

    // 테스트용 컨트롤러 클래스
    static class TestController {

        @RequirePermission(menu = "MM_MAT", authorities = {"AC_SYS", "AC_SAL", "AC_PRO", "AC_WHS"}, accessType = AccessType.READ)
        public void testMethod() {
            // 테스트용 메서드
        }

        @RequirePermission(menu = "MM_MAT", authorities = {"AC_SYS", "AC_SAL", "AC_PRO", "AC_WHS"}, accessType = AccessType.READ)
        public void testReadMethod() {
            // 읽기 권한 테스트용 메서드
        }

        @RequirePermission(menu = "MM_MAT", authorities = {"AC_SYS", "AC_SAL", "AC_PRO"}, accessType = AccessType.WRITE)
        public void testWriteMethod() {
            // 쓰기 권한 테스트용 메서드
        }
    }
}

/**
 * Spring Security 통합 후 추가할 실제 권한 체크 테스트
 *
 * TODO: Spring Security 추가 후 아래 테스트 케이스들을 구현하세요
 *
 * 1. 영업팀 (AC_SAL) 권한 테스트:
 *    - 자재 조회 성공
 *    - 자재 등록/수정/삭제 성공
 *    - 고객사 정보 조회 성공 (읽기 전용)
 *
 * 2. 생산팀 (AC_PRO) 권한 테스트:
 *    - 자재 조회 성공
 *    - 자재 등록/수정/삭제 성공
 *    - BOM 관리 성공
 *
 * 3. 물류팀 (AC_WHS) 권한 테스트:
 *    - 자재 조회 성공
 *    - 자재 등록/수정/삭제 실패 (읽기 전용)
 *
 * 4. 시스템 관리자 (AC_SYS) 권한 테스트:
 *    - 모든 기능 접근 성공
 *
 * 5. 복수 권한 (AC_SYS,AC_SAL) 테스트:
 *    - 권한 중 하나라도 매칭되면 접근 성공
 *
 * 6. 권한 없음 테스트:
 *    - 인증되지 않은 사용자 접근 실패 (UnauthorizedException)
 *    - 권한 부족한 사용자 접근 실패 (PermissionDeniedException)
 */
