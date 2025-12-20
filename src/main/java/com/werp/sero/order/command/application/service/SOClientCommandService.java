package com.werp.sero.order.command.application.service;

import com.werp.sero.employee.command.domain.aggregate.ClientEmployee;
import com.werp.sero.order.command.application.dto.SOClientOrderDTO;
import com.werp.sero.order.command.application.dto.SODetailResponseDTO;
import jakarta.validation.Valid;

public interface SOClientCommandService {
    SOClientOrderDTO createOrder(final ClientEmployee clientEmployee, final @Valid SOClientOrderDTO request);
}
