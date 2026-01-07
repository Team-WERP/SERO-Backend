package com.werp.sero.code.command.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "공통코드 타입 수정 요청 DTO")
public class CommonCodeTypeUpdateRequestDTO {

    @NotBlank(message = "코드명은 필수입니다")
    @Size(max = 50, message = "코드명은 50자 이하여야 합니다")
    @Schema(description = "타입명", example = "부서")
    private String name;

    @Size(max = 255, message = "설명은 255자 이하여야 합니다")
    @Schema(description = "설명", example = "부서 구분 코드")
    private String description;
}
