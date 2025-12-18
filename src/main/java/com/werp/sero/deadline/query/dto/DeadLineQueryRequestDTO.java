package com.werp.sero.deadline.query.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "납기 가능 여부 조회 요청하는 DTO")
public class DeadLineQueryRequestDTO {

    @Schema(description = "희망 수령일")
    private String desiredDeliveryDate;

    @Schema(description = "조회할 품목 목록")
    private List<ItemRequest> items;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "품목별 요청 정보")
    public static class ItemRequest {

        @Schema(description = "자재 코드")
        private String materialCode;

        @Schema(description = "주문 수량")
        private int quantity;

    }

}
