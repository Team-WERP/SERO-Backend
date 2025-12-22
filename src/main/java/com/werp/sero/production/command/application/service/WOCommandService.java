package com.werp.sero.production.command.application.service;

import com.werp.sero.employee.command.domain.aggregate.Employee;

public interface WOCommandService {

    void createFromProductionPlan(int ppId, Employee currentEmployee);
}
