package com.werp.sero.warehouse.query.dto;

import com.werp.sero.warehouse.command.domain.aggregate.Warehouse;
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

    public static WarehouseListResponseDTO from(Warehouse warehouse) {
        return WarehouseListResponseDTO.builder()
                .id(warehouse.getId())
                .warehouseName(warehouse.getName())
                .warehouseType(warehouse.getType())
                .build();
    }

}
