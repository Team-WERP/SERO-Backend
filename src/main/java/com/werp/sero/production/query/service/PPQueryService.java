package com.werp.sero.production.query.service;

import com.werp.sero.production.query.dto.PRItemPlanningResponseDTO;

public interface PPQueryService {
    PRItemPlanningResponseDTO getPlanning(int prItemId);
}
