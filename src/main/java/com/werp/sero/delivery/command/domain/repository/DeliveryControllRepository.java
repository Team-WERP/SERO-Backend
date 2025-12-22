package com.werp.sero.delivery.command.domain.repository;

import com.werp.sero.delivery.command.domain.aggregate.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeliveryControllRepository extends JpaRepository<Delivery, Integer> {

    // GI 코드로 배송 조회
    Optional<Delivery> findByGoodsIssue_GiCode(String giCode);
}
