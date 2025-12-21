package com.werp.sero.production.command.application.service;

import com.werp.sero.employee.command.domain.aggregate.Employee;
import com.werp.sero.production.command.application.dto.PPCreateRequestDTO;
import com.werp.sero.production.command.application.dto.PPCreateResponseDTO;
import com.werp.sero.production.command.application.dto.PPValidateRequestDTO;
import com.werp.sero.production.command.application.dto.PPValidationResponseDTO;

public interface PPCommandService {
    PPValidationResponseDTO validate(PPValidateRequestDTO request);

    PPCreateResponseDTO create(PPCreateRequestDTO request, Employee currnetEmployee);
}
