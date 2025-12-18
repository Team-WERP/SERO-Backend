package com.werp.sero.warehouse.query.service;

import com.werp.sero.warehouse.query.dao.WarehouseMapper;
import com.werp.sero.warehouse.query.dto.WarehouseListResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WarehouseListQueryServiceImpl implements WarehouseListQueryService {

    private final WarehouseMapper warehouseMapper;

    @Override
    public List<WarehouseListResponseDTO> getAllWarehouseList() {
        return warehouseMapper.findAll()
                .stream()
                .map(WarehouseListResponseDTO::from)
                .toList();
    }
}
