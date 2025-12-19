package com.werp.sero.shipping.command.application.service;

import com.werp.sero.shipping.command.application.dto.DOCreateRequestDTO;

public interface DeliveryOrderCommandService {

    String createDeliveryOrder(DOCreateRequestDTO requestDTO, int managerId);
}
