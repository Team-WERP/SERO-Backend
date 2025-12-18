package com.werp.sero.production.command.application.service;

import com.werp.sero.employee.command.domain.aggregate.Employee;
import com.werp.sero.production.command.application.dto.PRDraftCreateRequestDTO;

public interface PRCommandService {
    int createDraft(PRDraftCreateRequestDTO dto, Employee drafter);
}
