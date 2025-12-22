package com.werp.sero.delivery.command.application.service;

import com.werp.sero.employee.command.domain.aggregate.Employee;

public interface DeliveryControllCommandService {
    void startDelivery(String giCode, Employee driver);

    void completeDelivery(String giCode, Employee driver);

}
