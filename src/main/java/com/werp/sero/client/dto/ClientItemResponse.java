package com.werp.sero.client.dto;

import com.werp.sero.client.entity.ClientItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ClientItemResponse {
    private Integer id;
    private String itemCode;
    private String itemName;
    private String spec;
    private String unit;
    private Integer contractPrice;
    private Integer moq;
    private String status;

    public static ClientItemResponse from(ClientItem clientItem) {
        return ClientItemResponse.builder()
                .id(clientItem.getId())
                .itemCode(clientItem.getMaterial().getMaterialCode())
                .itemName(clientItem.getMaterial().getName())
                .spec(clientItem.getMaterial().getSpec())
                .unit(clientItem.getMaterial().getBaseUnit())
                .contractPrice(clientItem.getContractPrice())
                .moq(clientItem.getMaterial().getMoq())
                .status(clientItem.getStatus())
                .build();
    }
}
