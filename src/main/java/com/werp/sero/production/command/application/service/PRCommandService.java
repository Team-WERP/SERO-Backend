package com.werp.sero.production.command.application.service;

import com.werp.sero.employee.command.domain.aggregate.Employee;
import com.werp.sero.production.command.application.dto.PRDraftCreateRequestDTO;
import com.werp.sero.production.command.application.dto.PRDraftUpdateRequestDTO;

public interface PRCommandService {
    int createDraft(PRDraftCreateRequestDTO dto, Employee drafter);

    void updateDraft(int prId, PRDraftUpdateRequestDTO dto, Employee employee);

    void request(int prId, Employee employee);

    void assignManager(int prId, int managerId);
}
