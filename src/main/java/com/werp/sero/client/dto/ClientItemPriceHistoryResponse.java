package com.werp.sero.client.dto;

import com.werp.sero.client.entity.ClientItemPriceHistory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ClientItemPriceHistoryResponse {
    private int id;
    private int unitPrice;
    private int contractPrice;
    private String reason;
    private String name;
    private String status;
    private String createdAt;

    public static ClientItemPriceHistoryResponse from(ClientItemPriceHistory history) {
        return ClientItemPriceHistoryResponse.builder()
                .id(history.getId())
                .unitPrice(history.getUnitPrice())
                .contractPrice(history.getContract_price())
                .reason(history.getReason())
                .name(history.getName())
                .status(history.getStatus())
                .createdAt(history.getCreatedAt())
                .build();
    }
}
