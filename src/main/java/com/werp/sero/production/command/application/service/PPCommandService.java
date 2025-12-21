package com.werp.sero.production.command.application.service;

import com.werp.sero.production.command.application.dto.ProductionPlanValidateRequestDTO;
import com.werp.sero.production.command.application.dto.ProductionPlanValidationResponseDTO;

public interface PPCommandService {
    ProductionPlanValidationResponseDTO validate(ProductionPlanValidateRequestDTO request);
}
