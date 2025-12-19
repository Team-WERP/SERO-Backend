package com.werp.sero.order.command.application.service;

import com.werp.sero.employee.command.domain.aggregate.ClientEmployee;
import com.werp.sero.order.command.application.dto.SOClientOrderRequestDTO;
import com.werp.sero.order.command.application.dto.SODetailResponseDTO;
import jakarta.validation.Valid;

public interface SOClientCommandService {
    SODetailResponseDTO createOrder(final ClientEmployee clientEmployee, final @Valid SOClientOrderRequestDTO request);
}
