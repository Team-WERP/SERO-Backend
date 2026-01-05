package com.werp.sero.order.command.domain.repository;

import com.werp.sero.order.command.domain.aggregate.SalesOrderGoal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SOGoalRepository  extends JpaRepository<SalesOrderGoal, Integer> {
}
