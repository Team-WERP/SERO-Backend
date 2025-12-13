package com.werp.sero.order.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.werp.sero.order.entity.SalesOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDTO {

    @Schema(description = "주문 ID")
    private int id;

    @Schema(description = "주문 코드")
    private String soCode;

    @Schema(description = "고객사 이름")
    private String clientName;

    @Schema(description = "주문일시")
    private String orderedAt;

    @Schema(description = "납기일시")
    private String shippedAt;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "고객사 구매 주문 코드 (PO Code)")
    private String poCode;

    @Schema(description = "총 주문 수량")
    private int totalQuantity;

    @Schema(description = "총 주문 품목 수")
    private int totalItemCount;

    @Schema(description = "총 주문 금액")
    private long totalPrice;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "주문서 URL")
    private String soUrl;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "반려 사유")
    private String rejectionReason;

    @Schema(description = "대표 품목명")
    private String mainItemName;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "비고")
    private String note;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "결재 번호")
    private String approvalCode;

    @Schema(description = "주문 상태")
    private String status;

    @Schema(description = "배송지명")
    private String shippingName;

    @Schema(description = "배송지 주소")
    private String address;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "위도")
    private Double latitude;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "경도")
    private Double longitude;

    @Schema(description = "수령인 이름")
    private String recipientName;

    @Schema(description = "수령인 연락처")
    private String recipientContact;

    @Schema(description = "고객사 ID")
    private int clientId;

    @Schema(description = "고객사 담당자 ID")
    private int clientManagerId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "담당자 ID")
    private Integer managerId;


    public static OrderResponseDTO of(final SalesOrder salesOrder) {
        return new OrderResponseDTO(
                salesOrder.getId(),
                salesOrder.getSoCode(),
                salesOrder.getClientName(),
                salesOrder.getOrderedAt(),
                salesOrder.getShippedAt(),
                salesOrder.getPoCode(),
                salesOrder.getTotalQuantity(),
                salesOrder.getTotalItemCount(),
                salesOrder.getTotalPrice(),
                salesOrder.getSoUrl(),
                salesOrder.getRejectionReason(),
                salesOrder.getMainItemName(),
                salesOrder.getNote(),
                salesOrder.getApprovalCode(),
                salesOrder.getStatus(),
                salesOrder.getShippingName(),
                salesOrder.getAddress(),
                salesOrder.getLatitude(),
                salesOrder.getLongitude(),
                salesOrder.getRecipientName(),
                salesOrder.getRecipientContact(),
                salesOrder.getClient().getId(),
                salesOrder.getClientEmployee().getId(),
                salesOrder.getEmployee() != null ? salesOrder.getEmployee().getId() : null
        );
    }
}