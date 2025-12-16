package com.werp.sero.order.command.application.service;

import com.werp.sero.order.command.application.dto.SOCancelRequestDTO;
import com.werp.sero.order.command.application.dto.SODetailResponseDTO;
import jakarta.validation.Valid;

public interface SOCommandService {
    SODetailResponseDTO cancelOrder(final int orderId, @Valid SOCancelRequestDTO request);
}
