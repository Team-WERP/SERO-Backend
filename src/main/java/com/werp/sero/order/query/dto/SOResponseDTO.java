package com.werp.sero.order.query.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import com.werp.sero.employee.command.domain.aggregate.Employee;
import com.werp.sero.order.command.domain.aggregate.SalesOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SOResponseDTO {

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

    @Schema(description = "수령인 이름")
    private String recipientName;

    @Schema(description = "수령인 연락처")
    private String recipientContact;

    @Schema(description = "고객사 ID")
    private int clientId;

    @Schema(description = "고객사 담당자 ID")
    private int clientManagerId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "담당자 이름")
    private String managerName;

    public static SOResponseDTO of(final SalesOrder salesOrder) {

        String managerName = Optional.ofNullable(salesOrder.getEmployee())
                .map(Employee::getName)
                .orElse(null);

        return new SOResponseDTO(
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
                salesOrder.getRecipientName(),
                salesOrder.getRecipientContact(),
                salesOrder.getClient().getId(),
                salesOrder.getClientEmployee().getId(),
                managerName
        );
    }
}