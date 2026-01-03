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
public class SOClientDashboardResponseDTO {

    @Schema(description = "상단 주문 데이터")
    private SOClientStatDTO stats;

    @Schema(description = "공지사항 목록")
    private List<SOClientNoticeDTO> notices;

    @Schema(description = "최근 주문 목록")
    private List<SOClientDashboardListDTO> orderList;

    @Schema(description = "납기 7일 이내 주문 목록")
    private List<SOClientUrgentOrderDTO> urgentOrders;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SOClientStatDTO {
        @Schema(description = "납기 임박 주문 수")
        private int nearDeadlineCount;

        @Schema(description = "접수대기 주문수")
        private int pendingCount;

        @Schema(description = "진행중 주문 수")
        private int inProgressCount;

        @Schema(description = "완료 주문 수")
        private int doneCount;
    }


    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SOClientNoticeDTO {
        @Schema(description = "공지사항 id")
        private int id;

        @Schema(description = "제목")
        private String title;

        @Schema(description = "긴급 여부")
        private boolean isEmergency;

    }


    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SOClientDashboardListDTO {

        @Schema(description = "주문 ID")
        private int orderId;

        @Schema(description = "주문 번호")
        private String soCode;

        @Schema(description = "대표 품목명")
        private String mainItemName;

        @Schema(description = "총 품목 수")
        private int totalItemCount;

        @Schema(description = "총 품목 수량")
        private int totalQuantity;

        @Schema(description = "납기 요청일")
        private String shippedAt;

        @Schema(description = "진행 상태")
        private String status;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SOClientUrgentOrderDTO {

        @Schema(description = "주문 id")
        private int orderId;

        @Schema(description = "주문 번호")
        private String orderCode;

        @Schema(description = "PO 번호")
        private String poCode;

        @Schema(description = "납기일")
        private String shippedAt;

        @Schema(description = "D-Day 정보")
        private String dDay;

        @Schema(description = "주문 상태")
        private String status;

    }

}
