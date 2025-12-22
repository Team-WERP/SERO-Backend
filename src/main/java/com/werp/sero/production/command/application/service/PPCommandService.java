package com.werp.sero.production.command.application.service;

import com.werp.sero.employee.command.domain.aggregate.Employee;
import com.werp.sero.production.command.application.dto.*;

public interface PPCommandService {
    PPValidationResponseDTO validate(PPValidateRequestDTO request);

    PPCreateResponseDTO create(PPCreateRequestDTO request, Employee currnetEmployee);

    void addToPlanningTarget(PPAddTargetRequestDTO request, Employee currentEmployee);
}
