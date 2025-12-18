package com.werp.sero.warehouse.query.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WarehouseListResponseDTO {

    private int id;
    private String warehouseName;
    private String warehouseType;

}
