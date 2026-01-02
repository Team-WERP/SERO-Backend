package com.werp.sero.code.query.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "공통코드 상세 관리 응답 DTO")
public class CommonCodeDetailManageDTO {

    @Schema(description = "상세 코드", example = "HR")
    private String code;

    @Schema(description = "코드명", example = "인사팀")
    private String name;

    @Schema(description = "설명", example = "HR Dept")
    private String codeNameEng;

    @Schema(description = "참조값", example = "MGT")
    private String ref1;

    @Schema(description = "정렬 순서", example = "1")
    private Integer sortOrder;

    @Schema(description = "사용 여부", example = "Y")
    private String isUsed;
}
