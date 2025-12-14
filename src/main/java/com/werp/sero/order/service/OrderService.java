package com.werp.sero.order.service;

import com.werp.sero.order.dto.OrderDetailResponseDTO;
import com.werp.sero.order.dto.OrderResponseDTO;

import java.util.List;

public interface OrderService {
    List<OrderResponseDTO> findOrderList();

    OrderDetailResponseDTO findOrderDetails(final int orderId);
}
