package com.werp.sero.shipping.command.domain.repository;

import com.werp.sero.shipping.command.domain.aggregate.DeliveryOrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeliveryOrderItemRepository extends JpaRepository<DeliveryOrderItem, Integer> {
    List<DeliveryOrderItem> findByDeliveryOrderId(int deliveryOrderId);
}
