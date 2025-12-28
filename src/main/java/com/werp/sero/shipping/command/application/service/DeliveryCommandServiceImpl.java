package com.werp.sero.shipping.command.application.service;

import com.werp.sero.employee.command.domain.aggregate.Employee;
import com.werp.sero.notification.command.domain.aggregate.enums.NotificationType;
import com.werp.sero.notification.command.infrastructure.event.NotificationEvent;
import com.werp.sero.shipping.command.domain.aggregate.Delivery;
import com.werp.sero.shipping.command.domain.repository.DeliveryRepository;
import com.werp.sero.shipping.exception.DeliveryNotFoundException;
import com.werp.sero.shipping.exception.InvalidDeliveryStatusTransitionException;
import com.werp.sero.shipping.exception.UnauthorizedDeliveryUpdateException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class DeliveryCommandServiceImpl implements DeliveryCommandService {

    private final DeliveryRepository deliveryRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public void startDelivery(String giCode, Employee driver) {
        // 1. 배송 조회
        Delivery delivery = deliveryRepository.findByGoodsIssue_GiCode(giCode)
                .orElseThrow(DeliveryNotFoundException::new);

        // 2. 기사 검증
        if (!delivery.getDriverName().equals(driver.getName()) ||
                !delivery.getDriverContact().equals(driver.getContact())) {
            throw new UnauthorizedDeliveryUpdateException();
        }

        // 3. 상태 검증
        if (!delivery.getStatus().equals("SHIP_ISSUED")) {
            throw new InvalidDeliveryStatusTransitionException("배송 시작은 출고 완료 상태에서만 가능합니다.");
        }

        // 4. 배송 시작
        delivery.startDelivery();
        // @Transactional이 자동으로 변경사항 저장 (dirty checking)

        // 5. 출고지시 담당자에게 알림 발송
        if (delivery.getGoodsIssue().getManager() != null) {
            eventPublisher.publishEvent(new NotificationEvent(
                NotificationType.SHIPPING,
                "배송 출발",
                "출고지시 " + giCode + "의 배송이 출발했습니다. (기사: " + driver.getName() + ")",
                delivery.getGoodsIssue().getManager().getId(),
                "/warehouse/goods-issues/" + giCode
            ));
        }
    }

    @Override
    public void completeDelivery(String giCode, Employee driver) {
        // 1. 배송 조회
        Delivery delivery = deliveryRepository.findByGoodsIssue_GiCode(giCode)
                .orElseThrow(DeliveryNotFoundException::new);

        // 2. 기사 검증
        if (!delivery.getDriverName().equals(driver.getName()) ||
                !delivery.getDriverContact().equals(driver.getContact())) {
            throw new UnauthorizedDeliveryUpdateException();
        }

        // 3. 상태 검증
        if (!delivery.getStatus().equals("SHIP_ING")) {
            throw new InvalidDeliveryStatusTransitionException("배송 완료는 배송중 상태에서만 가능합니다.");
        }

        // 4. 배송 완료
        delivery.completeDelivery();

        // 5. 출고지시 담당자에게 알림 발송
        if (delivery.getGoodsIssue().getManager() != null) {
            eventPublisher.publishEvent(new NotificationEvent(
                NotificationType.SHIPPING,
                "배송 도착",
                "출고지시 " + giCode + "의 배송이 완료되었습니다. (기사: " + driver.getName() + ")",
                delivery.getGoodsIssue().getManager().getId(),
                "/warehouse/goods-issues/" + giCode
            ));
        }
    }
}
