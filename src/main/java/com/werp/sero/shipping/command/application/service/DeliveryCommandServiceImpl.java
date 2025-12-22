package com.werp.sero.shipping.command.application.service;

import com.werp.sero.employee.command.domain.aggregate.Employee;
import com.werp.sero.shipping.command.domain.aggregate.Delivery;
import com.werp.sero.shipping.command.domain.repository.DeliveryRepository;
import com.werp.sero.shipping.exception.DeliveryNotFoundException;
import com.werp.sero.shipping.exception.InvalidDeliveryStatusTransitionException;
import com.werp.sero.shipping.exception.UnauthorizedDeliveryUpdateException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class DeliveryCommandServiceImpl implements DeliveryCommandService {

    private final DeliveryRepository deliveryRepository;

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
    }
}
