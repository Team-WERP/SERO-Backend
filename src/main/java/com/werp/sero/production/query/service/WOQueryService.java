package com.werp.sero.production.query.service;

import com.werp.sero.production.query.dto.WOByDateResponseDTO;
import com.werp.sero.production.query.dto.WOByPRResponseDTO;
import com.werp.sero.production.query.dto.WOByPPResponseDTO;

import java.util.List;

public interface WOQueryService {
    List<WOByPPResponseDTO> getByProductionPlan(int ppId);

    List<WOByPRResponseDTO> getByProductionRequest(int prId);

    List<WOByDateResponseDTO> getByDate(String date);
}
