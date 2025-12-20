package com.werp.sero.order.command.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.werp.sero.order.command.domain.aggregate.SalesOrder;
import com.werp.sero.order.command.domain.aggregate.SalesOrderItem;
import com.werp.sero.order.query.dto.SOClientItemResponseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SOClientOrderDTO {

    @Schema(description = "주문 ID")
    private int orderId;

    @Schema(description = "납기 요청일", example = "2026-01-31 16:00")
    private String shippedAt;

    @Schema(description = "PO 번호", example = "PO-20251219-001")
    private String poCode;

    @Schema(description = "주문서 url")
    private String soUrl;

    @Schema(description = "주문 품목 목록")
    private List<SOClientItemResponseDTO> items;

    @Schema(description = "배송지명")
    private String shippingName;

    @Schema(description = "배송지 주소")
    private String address;

    @Schema(description = "수령인 이름")
    private String recipientName;

    @Schema(description = "수령인 연락처")
    private String recipientContact;

    @Schema(description = "특이사항")
    private String note;

    public static SOClientOrderDTO of(SalesOrder savedOrder, List<SalesOrderItem> savedItems) {
        return SOClientOrderDTO.builder()
                .orderId(savedOrder.getId())
                .shippedAt(savedOrder.getShippedAt())
                .poCode(savedOrder.getPoCode())
                .shippingName(savedOrder.getShippingName())
                .address(savedOrder.getAddress())
                .recipientName(savedOrder.getRecipientName())
                .recipientContact(savedOrder.getRecipientContact())
                .note(savedOrder.getNote())

                .items(savedItems.stream()
                        .map(SOClientItemResponseDTO::fromEntity)
                        .collect(Collectors.toList()))
                .build();
    }
}