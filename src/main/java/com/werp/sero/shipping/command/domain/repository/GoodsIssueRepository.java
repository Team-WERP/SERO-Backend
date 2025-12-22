package com.werp.sero.shipping.command.domain.repository;

import com.werp.sero.shipping.command.domain.aggregate.GoodsIssue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoodsIssueRepository extends JpaRepository<GoodsIssue, Integer> {
    boolean existsByDoCode(String doCode);
}
