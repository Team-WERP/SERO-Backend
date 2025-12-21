package com.werp.sero.production.command.application.service;

import com.werp.sero.employee.command.domain.aggregate.Employee;
import com.werp.sero.production.command.application.dto.ProductionPlanCreateRequestDTO;
import com.werp.sero.production.command.application.dto.ProductionPlanCreateResponseDTO;
import com.werp.sero.production.command.application.dto.ProductionPlanValidateRequestDTO;
import com.werp.sero.production.command.application.dto.ProductionPlanValidationResponseDTO;

public interface PPCommandService {
    ProductionPlanValidationResponseDTO validate(ProductionPlanValidateRequestDTO request);

    ProductionPlanCreateResponseDTO create(ProductionPlanCreateRequestDTO request, Employee currnetEmployee);
}
