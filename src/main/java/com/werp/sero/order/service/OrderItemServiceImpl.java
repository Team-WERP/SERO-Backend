package com.werp.sero.order.service;

import com.werp.sero.order.dto.OrderItemResponseDTO;
import com.werp.sero.order.entity.SalesOrderItem;
import com.werp.sero.order.repository.SalesOrderItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OrderItemServiceImpl implements OrderItemService{
    private final SalesOrderItemRepository salesOrderItemRepository;

    @Transactional(readOnly = true)
    @Override
    public List<OrderItemResponseDTO> findOrderItemsById(int orderId) {
        final List<SalesOrderItem> salesOrderItems = salesOrderItemRepository.findBySalesOrderId(orderId);

        return salesOrderItems.stream()
                .map(OrderItemResponseDTO::of)
                .collect(Collectors.toList());
    }
}
