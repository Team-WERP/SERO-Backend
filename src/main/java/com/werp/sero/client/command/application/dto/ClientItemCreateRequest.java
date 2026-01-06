package com.werp.sero.client.command.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "고객사 거래 품목 등록 요청 DTO")
public class ClientItemCreateRequest {

    @NotNull(message = "품목 ID는 필수입니다")
    @Schema(description = "품목 ID", example = "1")
    private Integer itemId;

    @NotNull(message = "계약 단가는 필수입니다")
    @Min(value = 0, message = "계약 단가는 0 이상이어야 합니다")
    @Schema(description = "계약 단가", example = "10000")
    private Integer contractPrice;
}
