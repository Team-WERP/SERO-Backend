package com.werp.sero.code.command.application.dto;

import com.werp.sero.code.command.domain.aggregate.CommonCode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "공통코드 응답 DTO")
public class CommonCodeResponseDTO {

    @Schema(description = "ID", example = "1")
    private final int id;

    @Schema(description = "타입 코드", example = "DEPT")
    private final String typeCode;

    @Schema(description = "코드", example = "DEPT_HR")
    private final String code;

    @Schema(description = "코드명", example = "인사팀")
    private final String name;

    @Schema(description = "상위 코드", example = "DEPT_MGT")
    private final String parentCode;

    @Schema(description = "정렬 순서", example = "1")
    private final int sortOrder;

    @Schema(description = "설명", example = "Human Resources Department")
    private final String description;

    @Schema(description = "사용 여부", example = "true")
    private final boolean isUsed;

    public CommonCodeResponseDTO(CommonCode commonCode) {
        this.id = commonCode.getId();
        this.typeCode = commonCode.getTypeCode();
        this.code = commonCode.getCode();
        this.name = commonCode.getName();
        this.parentCode = commonCode.getParentCode();
        this.sortOrder = commonCode.getSortOrder();
        this.description = commonCode.getDescription();
        this.isUsed = commonCode.isUsed();
    }
}
