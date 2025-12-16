package com.werp.sero.client.query.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ClientItemPriceHistoryResponseDTO {
    private Integer id;
    private Integer unitPrice;
    private Integer contractPrice;
    private String reason;
    private String name;
    private String status;
    private String createdAt;
}
