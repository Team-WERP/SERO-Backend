package com.werp.sero.shipping.command.domain.repository;

import com.werp.sero.shipping.command.domain.aggregate.DeliveryOrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryOrderItemRepository extends JpaRepository<DeliveryOrderItem, Integer> {
}
