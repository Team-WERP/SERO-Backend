package com.werp.sero.code.query.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "공통코드 타입 관리 응답 DTO")
public class CommonCodeTypeManageDTO {

    @Schema(description = "코드 타입", example = "DEPT_CODE")
    private String code;

    @Schema(description = "코드 타입명 (한글)", example = "부서")
    private String name;

    @Schema(description = "코드 타입 설명", example = "Department")
    private String description;
}
