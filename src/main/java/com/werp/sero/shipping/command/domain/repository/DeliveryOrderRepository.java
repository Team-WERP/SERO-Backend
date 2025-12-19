package com.werp.sero.shipping.command.domain.repository;

import com.werp.sero.shipping.command.domain.aggregate.DeliveryOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DeliveryOrderRepository extends JpaRepository<DeliveryOrder, Integer> {

    @Query("SELECT COUNT(d) FROM DeliveryOrder d WHERE d.doCode LIKE CONCAT('DO-', :today, '-%')")
    long countByToday(@Param("today") String today);
}
