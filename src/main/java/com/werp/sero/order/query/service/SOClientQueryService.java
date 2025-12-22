package com.werp.sero.order.query.service;

import com.werp.sero.employee.command.domain.aggregate.ClientEmployee;
import com.werp.sero.order.command.application.dto.SOClientOrderDTO;
import com.werp.sero.order.query.dto.SOClientDetailResponseDTO;
import com.werp.sero.order.query.dto.SOClientFilterDTO;
import com.werp.sero.order.query.dto.SOClientListResponseDTO;
import com.werp.sero.order.query.dto.SOClientResponseDTO;

import java.util.List;

public interface SOClientQueryService {
    List<SOClientResponseDTO> findOrderHistory(final ClientEmployee clientEmployee);

    SOClientResponseDTO getOrderForReorder(final int orderId, final ClientEmployee clientEmployee);

    List<SOClientListResponseDTO> findClientOrderList(final ClientEmployee clientEmployee, final SOClientFilterDTO filter, final Integer page);

    SOClientDetailResponseDTO findClientOrderDetail(final int orderId, final ClientEmployee clientEmployee);
}
