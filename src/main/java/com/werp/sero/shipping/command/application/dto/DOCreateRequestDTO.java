package com.werp.sero.shipping.command.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "납품서 생성 요청 DTO")
public class DOCreateRequestDTO {

    @NotNull
    @Schema(description = "주문 ID", example = "1")
    private Integer soId;

    @Schema(description = "특이사항", example = "파손 주의")
    private String note;

    @NotEmpty
    @Valid
    @Schema(description = "납품 품목 목록")
    private List<DOItemRequestDTO> items;
}
