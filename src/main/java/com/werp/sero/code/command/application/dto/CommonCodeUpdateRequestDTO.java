package com.werp.sero.code.command.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "공통코드 수정 요청 DTO")
public class CommonCodeUpdateRequestDTO {

    @NotBlank(message = "코드명은 필수입니다")
    @Size(max = 100, message = "코드명은 100자 이하여야 합니다")
    @Schema(description = "코드명", example = "인사팀")
    private String name;

    @Size(max = 50, message = "상위 코드는 50자 이하여야 합니다")
    @Schema(description = "상위 코드", example = "DEPT_MGT")
    private String parentCode;

    @NotNull(message = "정렬 순서는 필수입니다")
    @Schema(description = "정렬 순서", example = "1")
    private Integer sortOrder;

    @Size(max = 255, message = "설명은 255자 이하여야 합니다")
    @Schema(description = "설명", example = "Human Resources Department")
    private String description;

    @NotNull(message = "사용 여부는 필수입니다")
    @Schema(description = "사용 여부", example = "true")
    private Boolean isUsed;
}
