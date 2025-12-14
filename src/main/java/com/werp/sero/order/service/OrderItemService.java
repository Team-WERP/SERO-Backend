package com.werp.sero.order.service;

import com.werp.sero.order.dto.OrderItemResponseDTO;

import java.util.List;

public interface OrderItemService {
    List<OrderItemResponseDTO> findOrderItemsById(int orderId);
}
