package com.werp.sero.order.query.service;

import com.werp.sero.order.query.dao.SOItemMapper;
import com.werp.sero.order.query.dto.SOItemResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SOItemQueryServiceImpl implements SOItemQueryService {

    private final SOItemMapper orderItemMapper;

    @Transactional(readOnly = true)
    @Override
    public List<SOItemResponseDTO> findOrderItemsById(final int orderId) {
        return orderItemMapper.selectItemsBySalesOrderId(orderId);
    }
}
