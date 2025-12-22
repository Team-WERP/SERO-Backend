package com.werp.sero.shipping.command.domain.repository;

import com.werp.sero.shipping.command.domain.aggregate.DeliveryOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeliveryOrderRepository extends JpaRepository<DeliveryOrder, Integer> {
    Optional<DeliveryOrder> findByDoCode(String doCode);
}
