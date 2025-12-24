package com.werp.sero.shipping.command.application.service;

import com.werp.sero.employee.command.domain.aggregate.Employee;

public interface DeliveryCommandService {

    /**
     * Start delivery - Update status to SHIP_ING
     * @param giCode 출고지시 코드
     * @param driver 기사 정보 (authenticated employee)
     */
    void startDelivery(String giCode, Employee driver);

    /**
     * Complete delivery - Update status to SHIP_DONE
     * @param giCode 출고지시 코드
     * @param driver 기사 정보 (authenticated employee)
     */
    void completeDelivery(String giCode, Employee driver);
}
