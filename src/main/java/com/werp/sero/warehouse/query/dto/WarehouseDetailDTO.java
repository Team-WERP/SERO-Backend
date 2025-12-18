package com.werp.sero.warehouse.query.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WarehouseDetailDTO {

    private int id;
    private String name;
    private String address;
    private Double latitude;
    private Double longitude;
    private String type;
    private String createdAt;
    private String updatedAt;
}

