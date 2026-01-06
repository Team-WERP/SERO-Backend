package com.werp.sero.client.command.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientItemUpdateResponse {
    private Integer id;
    private Integer clientId;
    private Integer itemId;
    private String itemCode;
    private String itemName;
    private Integer contractPrice;
    private String status;
}
