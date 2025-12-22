package com.werp.sero.shipping.command.domain.repository;

import com.werp.sero.shipping.command.domain.aggregate.GoodsIssue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GIRepository extends JpaRepository<GoodsIssue, Integer> {
    Optional<GoodsIssue> findByGiCode(final String giCode);
}