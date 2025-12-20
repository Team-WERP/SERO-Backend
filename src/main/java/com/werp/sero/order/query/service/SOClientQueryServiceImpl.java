package com.werp.sero.order.query.service;

import com.werp.sero.employee.command.domain.aggregate.ClientEmployee;
import com.werp.sero.order.command.application.dto.SOClientOrderDTO;
import com.werp.sero.order.query.dao.SOClientMapper;
import com.werp.sero.order.query.dto.SOClientResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SOClientQueryServiceImpl implements SOClientQueryService {

    private final SOClientMapper soClientMapper;

    @Override
    @Transactional(readOnly = true)
    public List<SOClientResponseDTO> findOrderHistory(final ClientEmployee clientEmployee) {
        int clientId = clientEmployee.getId();

        return soClientMapper.selectOrderHistory(clientId);
    }

    @Override
    public SOClientResponseDTO getOrderForReorder(final int orderId, final ClientEmployee clientEmployee) {
        int clientId = clientEmployee.getId();

        return soClientMapper.selectOrderForReorder(clientId, orderId);
    }

}
