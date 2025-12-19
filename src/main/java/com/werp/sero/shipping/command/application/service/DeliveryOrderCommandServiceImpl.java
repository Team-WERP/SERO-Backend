package com.werp.sero.shipping.command.application.service;

import com.werp.sero.employee.command.domain.aggregate.Employee;
import com.werp.sero.employee.command.domain.repository.EmployeeRepository;
import com.werp.sero.order.command.domain.aggregate.SalesOrder;
import com.werp.sero.order.command.domain.aggregate.SalesOrderItem;
import com.werp.sero.order.command.domain.repository.SOItemRepository;
import com.werp.sero.order.command.domain.repository.SORepository;
import com.werp.sero.order.exception.SalesOrderItemNotFoundException;
import com.werp.sero.order.exception.SalesOrderNotFoundException;
import com.werp.sero.shipping.command.application.dto.DOCreateRequestDTO;
import com.werp.sero.shipping.command.application.dto.DOItemRequestDTO;
import com.werp.sero.shipping.command.domain.aggregate.DeliveryOrder;
import com.werp.sero.shipping.command.domain.aggregate.DeliveryOrderItem;
import com.werp.sero.shipping.command.domain.repository.DeliveryOrderItemRepository;
import com.werp.sero.shipping.command.domain.repository.DeliveryOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DeliveryOrderCommandServiceImpl implements DeliveryOrderCommandService {

    private final DeliveryOrderRepository deliveryOrderRepository;
    private final DeliveryOrderItemRepository deliveryOrderItemRepository;
    private final SORepository soRepository;
    private final SOItemRepository soItemRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    @Transactional
    public String createDeliveryOrder(DOCreateRequestDTO requestDTO, int managerId) {
        // 1. 주문 조회
        SalesOrder salesOrder = soRepository.findById(requestDTO.getSoId())
                .orElseThrow(SalesOrderNotFoundException::new);

        // 2. 작성자 (영업팀 직원) 조회
        Employee manager = employeeRepository.findById(managerId)
                .orElseThrow(() -> new RuntimeException("직원을 찾을 수 없습니다."));

        // 3. 납품서 코드 생성 (DO-YYYYMMDD-XXX 형식)
        String doCode = generateDeliveryOrderCode();

        // 4. 납품서 엔티티 생성
        DeliveryOrder deliveryOrder = DeliveryOrder.builder()
                .doCode(doCode)
                .doUrl("") // TODO: 문서 URL은 추후 처리
                .note(requestDTO.getNote())
                .createdAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .salesOrder(salesOrder)
                .manager(manager)
                .build();

        // 5. 납품서 저장
        DeliveryOrder savedDeliveryOrder = deliveryOrderRepository.save(deliveryOrder);

        // 6. 납품서 품목 저장
        List<DeliveryOrderItem> deliveryOrderItems = new ArrayList<>();

        // items가 null이거나 비어있으면 주문의 모든 품목을 자동으로 포함
        if (requestDTO.getItems() == null || requestDTO.getItems().isEmpty()) {
            List<SalesOrderItem> allOrderItems = soItemRepository.findBySalesOrderId(requestDTO.getSoId());

            for (SalesOrderItem salesOrderItem : allOrderItems) {
                DeliveryOrderItem deliveryOrderItem = DeliveryOrderItem.builder()
                        .doQuantity(salesOrderItem.getQuantity()) // 주문 수량 그대로 사용
                        .deliveryOrder(savedDeliveryOrder)
                        .salesOrderItem(salesOrderItem)
                        .build();

                deliveryOrderItems.add(deliveryOrderItem);
            }
        } else {
            // items가 있으면 지정된 품목만 포함
            for (DOItemRequestDTO itemDTO : requestDTO.getItems()) {
                SalesOrderItem salesOrderItem = soItemRepository.findById(itemDTO.getSoItemId())
                        .orElseThrow(SalesOrderItemNotFoundException::new);

                DeliveryOrderItem deliveryOrderItem = DeliveryOrderItem.builder()
                        .doQuantity(itemDTO.getDoQuantity())
                        .deliveryOrder(savedDeliveryOrder)
                        .salesOrderItem(salesOrderItem)
                        .build();

                deliveryOrderItems.add(deliveryOrderItem);
            }
        }

        // 7. 납품서 품목 일괄 저장
        deliveryOrderItemRepository.saveAll(deliveryOrderItems);

        return doCode;
    }

    /**
     * 납품서 코드 생성 (DO-YYYYMMDD-XX)
     * 예: DO-20251219-01, DO-20251219-02, ..., DO-20251219-99, DO-20251219-100
     */
    private String generateDeliveryOrderCode() {
        String today = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        // 오늘 날짜로 생성된 납품서 개수 조회
        long count = deliveryOrderRepository.countByToday(today);

        // 다음 순번 (1부터 시작)
        int nextSequence = (int) (count + 1);

        // 01~99는 2자리, 100 이상은 그대로 표시
        String sequence = nextSequence < 100
            ? String.format("%02d", nextSequence)
            : String.valueOf(nextSequence);

        return String.format("DO-%s-%s", today, sequence);
    }
}
