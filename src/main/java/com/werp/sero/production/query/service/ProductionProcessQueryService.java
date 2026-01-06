package com.werp.sero.production.query.service;

import com.werp.sero.production.query.dto.LineMaterialListItemDTO;
import com.werp.sero.production.query.dto.ProductionProcessResponseDTO;

import java.util.List;

public interface ProductionProcessQueryService {
    List<LineMaterialListItemDTO> getLineMaterialList();

    List<ProductionProcessResponseDTO> getProcessList(int lineMaterialId);
}
