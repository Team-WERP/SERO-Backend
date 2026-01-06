package com.werp.sero.client.command.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
@Schema(description = "고객사 거래 품목 등록 응답 DTO")
public class ClientItemCreateResponse {

    @Schema(description = "거래 품목 ID", example = "1")
    private Integer id;

    @Schema(description = "고객사 ID", example = "1")
    private Integer clientId;

    @Schema(description = "품목 ID", example = "1")
    private Integer itemId;

    @Schema(description = "품목 코드", example = "MAT-001")
    private String itemCode;

    @Schema(description = "품목명", example = "완제품A")
    private String itemName;

    @Schema(description = "계약 단가", example = "10000")
    private Integer contractPrice;

    @Schema(description = "상태", example = "TRADE_NORMAL")
    private String status;
}
