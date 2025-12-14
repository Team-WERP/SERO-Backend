package com.werp.sero.order.service;

import com.werp.sero.employee.entity.Employee;
import com.werp.sero.employee.exception.EmployeeNotFoundException;
import com.werp.sero.order.dto.OrderCancelRequestDTO;
import com.werp.sero.order.dto.OrderDetailResponseDTO;
import com.werp.sero.order.dto.OrderResponseDTO;
import com.werp.sero.order.entity.SalesOrder;
import com.werp.sero.order.exception.OrderNotFoundException;
import com.werp.sero.order.repository.EmployeeRepository;
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
    private final EmployeeRepository employeeRepository;

    @Transactional(readOnly = true)
    @Override
    public List<OrderResponseDTO> findOrderList() {

        final List<SalesOrder> salesOrders = salesOrderRepository.findAll();

        return salesOrders.stream()
                .map(OrderResponseDTO::of)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public OrderDetailResponseDTO findOrderDetails(final int orderId) {
        final SalesOrder salesOrder = salesOrderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("주문을 찾을 수 없습니다."));

        return OrderDetailResponseDTO.of(salesOrder);
    }

    @Transactional
    @Override
    public void assignManagerToOrder(final int orderId, final int empId) {
        SalesOrder salesOrder = salesOrderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("주문을 찾을 수 없습니다."));

        Employee manager = employeeRepository.findById(empId)
                .orElseThrow(() -> new EmployeeNotFoundException("담당 직원을 찾을 수 없습니다."));

        salesOrder.updateManager(manager);

        salesOrderRepository.save(salesOrder);
    }

    @Transactional
    @Override
    public OrderDetailResponseDTO cancelOrder(final int orderId, final OrderCancelRequestDTO request) {
        SalesOrder salesOrder = salesOrderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("주문을 찾을 수 없습니다."));

        salesOrder.cancel(request.getRejectionReason());

        return OrderDetailResponseDTO.of(salesOrder);
    }
}
