package com.werp.sero.order.query.service;

import com.werp.sero.employee.command.domain.aggregate.ClientEmployee;
import com.werp.sero.order.query.dao.SOClientMapper;
import com.werp.sero.order.query.dto.SOClientResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SOClientQueryServiceImpl implements SOClientQueryService {

    private final SOClientMapper soClientMapper;

    @Override
    public List<SOClientResponseDTO> findOrderHistory(ClientEmployee clientEmployee) {
        int clientId = clientEmployee.getId();

        return soClientMapper.selectOrderHistory(clientId);
    }
}
