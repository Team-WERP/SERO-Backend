package com.werp.sero.common.swagger;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Swagger 에러 응답 어노테이션 모음
 * - 컨트롤러에서 재사용 가능한 에러 응답 정의
 */
public class ApiErrorResponses {

    /**
     * 404 Not Found - 리소스를 찾을 수 없음
     */
    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @ApiResponse(
            responseCode = "404",
            description = "리소스를 찾을 수 없음",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = com.werp.sero.common.error.ErrorResponse.class)
            )
    )
    public @interface NotFound {
        String code() default "";
        String message() default "";
    }

    /**
     * 자재 관련 에러 응답
     */
    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(
                    responseCode = "404",
                    description = "자재를 찾을 수 없음",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = com.werp.sero.common.error.ErrorResponse.class),
                            examples = @ExampleObject(
                                    value = "{\"code\": \"MATERIAL001\", \"message\": \"자재 정보를 찾을 수 없습니다.\", \"timestamp\": \"2025-12-16 14:30:25\"}"
                            )
                    )
            )
    })
    public @interface MaterialNotFound {}

    /**
     * 자재 코드 중복 에러 응답
     */
    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(
                    responseCode = "409",
                    description = "자재 코드 중복",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = com.werp.sero.common.error.ErrorResponse.class),
                            examples = @ExampleObject(
                                    value = "{\"code\": \"MATERIAL002\", \"message\": \"이미 존재하는 자재 코드입니다.\", \"timestamp\": \"2025-12-16 14:30:25\"}"
                            )
                    )
            )
    })
    public @interface MaterialCodeDuplicated {}

    /**
     * 창고 재고 관련 에러 응답
     */
    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(
                    responseCode = "404",
                    description = "창고 재고를 찾을 수 없음",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = com.werp.sero.common.error.ErrorResponse.class),
                            examples = @ExampleObject(
                                    value = "{\"code\": \"WAREHOUSE001\", \"message\": \"창고 재고 정보를 찾을 수 없습니다.\", \"timestamp\": \"2025-12-16 14:30:25\"}"
                            )
                    )
            )
    })
    public @interface WarehouseStockNotFound {}

    /**
     * 창고 재고 검색 유효성 에러 응답 (창고 ID, 자재 유형, 재고 상태)
     */
    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(
                    responseCode = "400",
                    description = "잘못된 검색 조건",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = com.werp.sero.common.error.ErrorResponse.class),
                            examples = {
                                    @ExampleObject(
                                            name = "자재 유형 오류",
                                            value = "{\"code\": \"WAREHOUSE003\", \"message\": \"잘못된 자재 유형입니다. (허용: MAT_FG, MAT_RM)\", \"timestamp\": \"2025-12-16 14:30:25\"}"
                                    ),
                                    @ExampleObject(
                                            name = "재고 상태 오류",
                                            value = "{\"code\": \"WAREHOUSE004\", \"message\": \"잘못된 재고 상태입니다. (허용: NORMAL, LOW, OUT_OF_STOCK)\", \"timestamp\": \"2025-12-16 14:30:25\"}"
                                    )
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "창고를 찾을 수 없음",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = com.werp.sero.common.error.ErrorResponse.class),
                            examples = @ExampleObject(
                                    value = "{\"code\": \"WAREHOUSE002\", \"message\": \"창고 정보를 찾을 수 없습니다.\", \"timestamp\": \"2025-12-16 14:30:25\"}"
                            )
                    )
            )
    })
    public @interface WarehouseStockSearchValidation {}
}
