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

    private int totalItemCount;
    private int totalQuantity;
    private long totalPrice;

    @Schema(description = "본사 담당자 이름")
    private String managerName;
    @Schema(description = "본사 담당자 연락처")
    private String managerContact;

    private String shippingName;
    private String address;
    private String recipientName;
    private String recipientContact;
    private String note;
}