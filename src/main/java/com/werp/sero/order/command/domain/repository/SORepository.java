package com.werp.sero.order.command.domain.repository;

import com.werp.sero.order.command.domain.aggregate.SalesOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SORepository extends JpaRepository<SalesOrder, Integer> {
    Optional<SalesOrder> findBySoCode(String soCode);
}
