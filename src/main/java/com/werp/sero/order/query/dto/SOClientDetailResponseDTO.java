package com.werp.sero.order.query.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SOClientDetailResponseDTO {

    @Schema(description = "주문 ID")
    private int orderId;

    @Schema(description = "고객사 ID")
    private int clientId;

    @Schema(description = "주문 번호")
    private String soCode;

    @Schema(description = "PO 번호")
    private String poCode;

    @Schema(description = "주문 상태 (매핑된 코드)")
    private String status;

    @Schema(description = "주문일")
    private String orderedAt;

    @Schema(description = "납기 요청일")
    private String shippedAt;

    @Schema(description = "주문서 URL")
    private String soUrl;

    @Schema(description = "납품서 URL")
    private List<String> doUrls;

    @Schema(description = "품목 리스트")
    private List<SOClientItemResponseDTO> items;

    @Schema(description = "총 주문 수량")
    private int totalItemCount;

    @Schema(description = "총 주문 품목 수")
    private int totalQuantity;

    @Schema(description = "총 주문 금액")
    private long totalPrice;

    @Schema(description = "본사 담당자 이름")
    private String managerName;

    @Schema(description = "본사 담당자 연락처")
    private String managerContact;

    @Schema(description = "배송지 이름")
    private String shippingName;

    @Schema(description = "배송지 주소")
    private String address;

    @Schema(description = "수령인 이름")
    private String recipientName;

    @Schema(description = "수령인 연락처")
    private String recipientContact;

    @Schema(description = "비고")
    private String note;
}