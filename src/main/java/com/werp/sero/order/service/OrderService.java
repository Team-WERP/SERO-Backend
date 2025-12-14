package com.werp.sero.order.service;

import com.werp.sero.order.dto.OrderCancelRequestDTO;
import com.werp.sero.order.dto.OrderDetailResponseDTO;
import com.werp.sero.order.dto.OrderResponseDTO;
import jakarta.validation.Valid;

import java.util.List;

public interface OrderService {
    List<OrderResponseDTO> findOrderList();

    OrderDetailResponseDTO findOrderDetails(final int orderId);

    void assignManagerToOrder(final int orderId, final int empId);

    OrderDetailResponseDTO cancelOrder(int orderId, @Valid OrderCancelRequestDTO request);
}
