package com.werp.sero.client.query.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ClientItemResponseDTO {
    private Integer id;
    private String itemCode;
    private String itemName;
    private String spec;
    private String unit;
    private Integer contractPrice;
    private Integer moq;
    private String status;
}
