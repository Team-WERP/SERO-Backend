package com.werp.sero.order.query.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SOClientListResponseDTO {

    @Schema(description = "주문 ID")
    private int orderId;

    @Schema(description = "주문 번호")
    private String soCode;

    @Schema(description = "PO 번호")
    private String poCode;

    @Schema(description = "대표 품목명")
    private String mainItemName;

    @Schema(description = "총 금액")
    private long totalPrice;

    @Schema(description = "총 품목 수")
    private int totalItemCount;

    @Schema(description = "주문일", example = "2025-12-07")
    private String orderedAt;

    @Schema(description = "납기 요청일", example = "2025-12-20")
    private String shippedAt;

    @Schema(description = "진행 상태")
    private String status;

    @Schema(description = "주문서 URL")
    private String soUrl;

    public static String convertStatus(String status) {
        if (status == null) return "-";
        return switch (status) {
            case "ORD_RED" -> "CLI_SO_RED";
            case "ORD_RVW", "ORD_APPR_PEND", "ORD_APPR_DONE", "ORD_WORK_REQ", "ORD_PRO", "ORD_SHIP_READY" -> "CLI_SO_ING";
            case "ORD_SHIPPING" -> "CLI_SO_SHIPPING";
            case "ORD_DONE" -> "CLI_SO_DONE";
            case "ORD_CANCEL" -> "CLI_SO_CANCEL";
            default -> "알수없음";
        };
    }
}