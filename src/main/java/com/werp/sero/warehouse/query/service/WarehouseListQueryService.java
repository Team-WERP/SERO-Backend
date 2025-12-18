package com.werp.sero.warehouse.query.service;

import com.werp.sero.warehouse.query.dto.WarehouseListResponseDTO;

import java.util.List;

public interface WarehouseListQueryService {
    List<WarehouseListResponseDTO> getAllWarehouseList();
}
