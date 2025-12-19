package com.werp.sero.order.command.application.dto;

import com.werp.sero.client.query.dto.ClientAddressResponseDTO;
import com.werp.sero.order.query.dto.SOClientItemResponseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SOClientOrderRequestDTO {

    @Schema(description = "납기 요청일", example = "2026-01-31 16:00:00")
    private String shipped_at;

    @Schema(description = "PO 번호", example = "PO-20251219-001")
    private String poCode;

    @Schema(description = "주문 품목 목록")
    private List<SOClientItemResponseDTO> items;

    @Schema(description = "배송지")
    private ClientAddressResponseDTO address;

    @Schema(description = "특이사항")
    private String note;

}