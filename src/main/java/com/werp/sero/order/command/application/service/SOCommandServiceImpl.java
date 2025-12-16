package com.werp.sero.order.command.application.service;

import com.werp.sero.order.command.application.dto.SOCancelRequestDTO;
import com.werp.sero.order.command.application.dto.SODetailResponseDTO;
import com.werp.sero.order.command.domain.aggregate.SalesOrder;
import com.werp.sero.order.command.domain.repository.SORepository;
import com.werp.sero.order.exception.SalesOrderNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class SOCommandServiceImpl implements SOCommandService{
    private final SORepository orderRepository;

    @Transactional
    @Override
    public SODetailResponseDTO cancelOrder(int orderId, SOCancelRequestDTO request) {
        SalesOrder salesOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new SalesOrderNotFoundException("주문을 찾을 수 없습니다."));

        salesOrder.cancel(request.getRejectionReason());

        return SODetailResponseDTO.of(salesOrder);
    }
}
