package com.werp.sero.order.query.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SOClientResponseDTO {

    @Schema(description = "주문 번호")
    private String soCode;

    @Schema(description = "대표 품목명")
    private String mainItemName;

    @Schema(description = "총 수량")
    private int totalQuantity;

    @Schema(description = "총 금액")
    private long totalPrice;

    @Schema(description = "주문일")
    private String orderedAt;

    @Schema(description = "주문 품목 목록")
    private List<SOClientItemResponseDTO> items;

}
