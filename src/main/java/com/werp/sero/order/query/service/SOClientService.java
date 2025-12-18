package com.werp.sero.order.query.service;

import com.werp.sero.employee.command.domain.aggregate.ClientEmployee;
import com.werp.sero.order.query.dto.SOClientResponseDTO;
import com.werp.sero.order.query.dto.SOResponseDTO;

import java.util.List;

public interface SOClientService {
    List<SOClientResponseDTO> findOrderHistory(final ClientEmployee clientEmployee);
}
