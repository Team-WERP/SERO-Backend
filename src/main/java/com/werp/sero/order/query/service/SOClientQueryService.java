package com.werp.sero.order.query.service;

import com.werp.sero.employee.command.domain.aggregate.ClientEmployee;
import com.werp.sero.order.command.application.dto.SOClientOrderDTO;
import com.werp.sero.order.query.dto.SOClientResponseDTO;

import java.util.List;

public interface SOClientQueryService {
    List<SOClientResponseDTO> findOrderHistory(final ClientEmployee clientEmployee);

    SOClientResponseDTO getOrderForReorder(final int orderId, final ClientEmployee clientEmployee);
}
