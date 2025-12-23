package com.werp.sero.production.command.application.service;

import com.werp.sero.employee.command.domain.aggregate.Employee;
import com.werp.sero.production.command.application.dto.WorkOrderCreateRequestDTO;

public interface WOCommandService {

    void createWorkOrder(WorkOrderCreateRequestDTO request, Employee currentEmployee);

    void start(int woId, String note);
}
