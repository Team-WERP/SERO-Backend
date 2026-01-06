package com.werp.sero.production.query.service;

import com.werp.sero.production.query.dao.ProductionProcessQueryMapper;
import com.werp.sero.production.query.dto.LineMaterialListItemDTO;
import com.werp.sero.production.query.dto.ProductionProcessResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductionProcessQueryServiceImpl implements ProductionProcessQueryService{
    private final ProductionProcessQueryMapper productionProcessQueryMapper;

    @Override
    @Transactional(readOnly = true)
    public List<LineMaterialListItemDTO> getLineMaterialList() {
        return productionProcessQueryMapper.selectLineMaterialList();
    }

    @Override
    public List<ProductionProcessResponseDTO> getProcessList(int lineMaterialId) {
        return productionProcessQueryMapper.selectProcessByLineMaterial(lineMaterialId);
    }
}
