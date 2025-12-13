package com.werp.sero.order.service;

import com.werp.sero.order.dto.OrderResponseDTO;
import com.werp.sero.order.entity.SalesOrder;
import com.werp.sero.order.repository.SalesOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {
    private final SalesOrderRepository salesOrderRepository;

    @Transactional(readOnly = true)
    @Override
    public List<OrderResponseDTO> findOrderList() {

        final List<SalesOrder> salesOrders = salesOrderRepository.findAll();

        return salesOrders.stream()
                .map(OrderResponseDTO::of)
                .collect(Collectors.toList());
    }
}
