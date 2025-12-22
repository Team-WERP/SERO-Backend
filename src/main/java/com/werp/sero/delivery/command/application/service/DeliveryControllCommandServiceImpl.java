package com.werp.sero.delivery.command.application.service;


import com.werp.sero.delivery.command.domain.aggregate.Delivery;
import com.werp.sero.delivery.command.domain.repository.DeliveryControllRepository;
import com.werp.sero.delivery.exception.InvalidDeliveryStatusTransitionException;
import com.werp.sero.delivery.exception.UnauthorizedDeliveryUpdateException;
import com.werp.sero.employee.command.domain.aggregate.Employee;
import com.werp.sero.shipping.exception.DeliveryOrderNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class DeliveryControllCommandServiceImpl implements DeliveryControllCommandService {

    private final DeliveryControllRepository deliveryControllRepository;

    // 배송 시작: SHIP_ISSUED -> SHIP_ING
    @Override
    public void startDelivery(String giCode, Employee driver) {

        // 1. 배송 조회
        Delivery delivery = deliveryControllRepository.findByGoodsIssue_GiCode(giCode)
                .orElseThrow(DeliveryOrderNotFoundException::new);

        // 2. 기사 검증
        if (!delivery.getDriverName().equals(driver.getName()) ||
            !delivery.getDriverContact().equals(driver.getContact())) {
            throw new UnauthorizedDeliveryUpdateException();
        }

        // 3. 상태 검증
        if (!delivery.getStatus().equals("SHIP_ISSUED")) {
            throw new InvalidDeliveryStatusTransitionException();
        }

        // 4. 상태 변경
        delivery.startDelivery();
    }

    @Override
    public void completeDelivery(String giCode, Employee driver) {

        // 1. 배송 조회
        Delivery delivery = deliveryControllRepository.findByGoodsIssue_GiCode(giCode)
                .orElseThrow(DeliveryOrderNotFoundException::new);


        // 2. 기사 검증
        if (!delivery.getDriverName().equals(driver.getName()) ||
                !delivery.getDriverContact().equals(driver.getContact())) {
            throw new UnauthorizedDeliveryUpdateException();
        }

        // 3. 상태 검증
        if (!delivery.getStatus().equals("SHIP_ING")) {
            throw new InvalidDeliveryStatusTransitionException();
        }

        // 4. 상태 변경
        delivery.completeDelivery();
    }
}
