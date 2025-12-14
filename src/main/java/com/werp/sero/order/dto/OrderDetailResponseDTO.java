package com.werp.sero.order.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.werp.sero.client.entity.Client;
import com.werp.sero.order.entity.SalesOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailResponseDTO {
    @Schema(description = "주문 ID")
    private int id;

    @Schema(description = "주문 코드")
    private String soCode;

    @Schema(description = "고객사 이름")
    private String clientName;

    @Schema(description = "주문 일자")
    private String orderedAt;

    @Schema(description = "납기 일자")
    private String shippedAt;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "고객사 구매 주문 코드")
    private String poCode;

    @Schema(description = "총 수량")
    private int totalQuantity;

    @Schema(description = "총 품목 수")
    private int totalItemCount;

    @Schema(description = "총 가격")
    private long totalPrice;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "주문서 URL")
    private String soUrl;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "반려 사유")
    private String rejectionReason;

    @Schema(description = "주요 품목명")
    private String mainItemName;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "비고")
    private String note;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "승인 코드")
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

    @Schema(description = "고객사 담당자명")
    private String clientManagerName;

    @Schema(description = "고객사 담당자 연락처")
    private String clientManagerContact;

    @Schema(description = "여신 한도")
    private long creditLimit;

    @Schema(description = "주문 가능 금액")
    private long orderAvailableAmount;

    @Schema(description = "고객사 ID")
    private int clientId;

    @Schema(description = "고객사 담당자 ID")
    private int clientManagerId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "본사 담당자 ID")
    private Integer managerId;

    public static OrderDetailResponseDTO of(final SalesOrder salesOrder) {

        final Client client = salesOrder.getClient();

        // 주문 가능 금액
        final long orderAvailableAmount = client.getCreditLimit() - client.getReceivables();

        return new OrderDetailResponseDTO(
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

                client.getManagerName(),
                client.getManagerContact(),
                client.getCreditLimit(),
                orderAvailableAmount,

                client.getId(),
                salesOrder.getClientEmployee().getId(),
                salesOrder.getEmployee() != null ? salesOrder.getEmployee().getId() : null
        );
    }
}