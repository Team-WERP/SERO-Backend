package com.werp.sero.shipping.command.domain.repository;

import com.werp.sero.shipping.command.domain.aggregate.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface DeliveryRepository extends JpaRepository<Delivery, Integer> {

    @Query("SELECT COUNT(d) FROM Delivery d WHERE d.trackingNumber LIKE CONCAT('SERO-', :date, '-%')")
    int countByDate(@Param("date") String date);

    // GI 코드로 배송 조회 (GoodsIssue 관계 활용)
    Optional<Delivery> findByGoodsIssue_GiCode(String giCode);
}
