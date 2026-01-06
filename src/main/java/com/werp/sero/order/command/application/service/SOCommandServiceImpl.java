package com.werp.sero.order.command.application.service;

import com.werp.sero.employee.command.domain.aggregate.Employee;
import com.werp.sero.employee.command.domain.repository.EmployeeRepository;
import com.werp.sero.employee.exception.EmployeeNotFoundException;
import com.werp.sero.notification.command.domain.aggregate.enums.NotificationType;
import com.werp.sero.notification.command.infrastructure.event.NotificationEvent;
import com.werp.sero.order.command.application.dto.SOCancelRequestDTO;
import com.werp.sero.order.command.application.dto.SODetailResponseDTO;
import com.werp.sero.order.command.application.dto.SOGoalResponseDTO;
import com.werp.sero.order.command.domain.aggregate.SalesOrder;
import com.werp.sero.order.command.domain.aggregate.SalesOrderGoal;
import com.werp.sero.order.command.domain.aggregate.enums.SalesOrderNotificationType;
import com.werp.sero.order.command.domain.repository.SOGoalRepository;
import com.werp.sero.order.command.domain.repository.SORepository;
import com.werp.sero.order.exception.OrderCannotBeCanceledException;
import com.werp.sero.order.exception.SalesOrderMonthlyGoalNotFoundException;
import com.werp.sero.order.exception.SalesOrderNotFoundException;
import jakarta.persistence.criteria.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SOCommandServiceImpl implements SOCommandService{
    private final SORepository orderRepository;
    private final EmployeeRepository employeeRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    @Override
    public SODetailResponseDTO cancelOrder(final int orderId, final SOCancelRequestDTO request) {
        SalesOrder salesOrder = orderRepository.findById(orderId)
                .orElseThrow(SalesOrderNotFoundException::new);

        // 주문 검토 및 결재 반려시에만 주문 취소 가능
        String currentStatus = salesOrder.getStatus();
        if (!"ORD_RVW".equals(currentStatus) && !"ORD_APPR_RJCT".equals(currentStatus)) {
            throw new OrderCannotBeCanceledException();
        }
        salesOrder.cancel(request.getRejectionReason());

        eventPublisher.publishEvent(NotificationEvent.forClient(
                NotificationType.ORDER,
                "주문 취소",
                "주문번호 " + salesOrder.getSoCode() + "가 취소되었습니다. 사유를 확인해주세요",
                salesOrder.getClientEmployee().getId(),
                "client-portal/order/management" + salesOrder.getId()
        ));

        return SODetailResponseDTO.of(salesOrder);
    }

    @Transactional
    @Override
    public SODetailResponseDTO assignManager(final int orderId, final int empId) {
        SalesOrder salesOrder = orderRepository.findById(orderId)
                .orElseThrow(SalesOrderNotFoundException::new);

        Employee manager = employeeRepository.findById(empId)
                .orElseThrow(EmployeeNotFoundException::new);

        salesOrder.updateManager(manager);

        return SODetailResponseDTO.of(salesOrder);
    }
}
