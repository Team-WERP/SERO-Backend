package com.werp.sero.config;

import com.werp.sero.common.security.RequirePermission;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;

import java.util.Arrays;
import java.util.stream.Collectors;

@Configuration
public class SwaggerPermissionConfig {

    @Bean
    public OperationCustomizer requirePermissionCustomizer() {
        return (operation, handlerMethod) -> {

            RequirePermission permission =
                    handlerMethod.getMethodAnnotation(RequirePermission.class);

            if (permission == null) {
                permission = handlerMethod
                        .getBeanType()
                        .getAnnotation(RequirePermission.class);
            }

            if (permission != null) {
                String permissionDescription = buildPermissionDescription(permission);

                operation.setDescription(
                        (operation.getDescription() != null
                                ? operation.getDescription() + "\n\n"
                                : "")
                                + permissionDescription
                );
            }

            return operation;
        };
    }

    private String buildPermissionDescription(RequirePermission permission) {

        String authorities = Arrays.stream(permission.authorities())
                .map(code -> "- " + code + " (" + mapPermissionName(code) + ")")
                .collect(Collectors.joining("\n"));

        return """
                ###  권한 정보 (Menu Permission)
                - **메뉴 코드**: %s
                - **접근 유형**: %s
                - **허용 권한**
                %s
                """.formatted(
                permission.menu(),
                permission.accessType(),
                authorities
        );
    }

    /**
     * 권한 코드 → 한글명 매핑
     */
    private String mapPermissionName(String code) {
        return switch (code) {
            case "AC_SYS" -> "시스템 관리자";
            case "AC_SAL" -> "영업";
            case "AC_PRO" -> "생산";
            case "AC_WHS" -> "물류";
            case "AC_CLI" -> "고객";
            default -> code;
        };
    }
}
