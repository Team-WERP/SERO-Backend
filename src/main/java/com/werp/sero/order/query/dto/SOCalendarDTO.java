package com.werp.sero.order.query.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SOCalendarDTO {
    @Schema(description = "선택 날짜")
    private String date;

    @Schema(description = "주문 건수")
    private int count;

    @Schema(description = "주문 목록")
    private List<CalendarDetail> dailyOrders;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CalendarDetail {
        @Schema(description = "주문 id")
        private int orderId;

        @Schema(description = "주문 번호")
        private String orderCode;

        @Schema(description = "주문 상태")
        private String status;

        @Schema(description = "고객사 이름")
        private String clientName;

        @Schema(description = "납기 요청 시간")
        private String shippedTime;

    }
}