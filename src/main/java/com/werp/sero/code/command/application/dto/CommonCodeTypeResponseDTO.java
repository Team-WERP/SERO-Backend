package com.werp.sero.code.command.application.dto;

import com.werp.sero.code.command.domain.aggregate.CommonCodeType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "공통코드 타입 응답 DTO")
public class CommonCodeTypeResponseDTO {

    @Schema(description = "ID", example = "1")
    private final int id;

    @Schema(description = "타입 코드", example = "DEPT")
    private final String code;

    @Schema(description = "타입명", example = "부서")
    private final String name;

    @Schema(description = "설명", example = "부서 구분 코드")
    private final String description;

    public CommonCodeTypeResponseDTO(CommonCodeType commonCodeType) {
        this.id = commonCodeType.getId();
        this.code = commonCodeType.getCode();
        this.name = commonCodeType.getName();
        this.description = commonCodeType.getDescription();
    }
}
