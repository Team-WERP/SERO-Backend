package com.werp.sero.approval.command.domain.repository;

import com.werp.sero.approval.command.domain.aggregate.Approval;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApprovalRepository extends JpaRepository<Approval, Integer> {
    boolean existsByRefCode(final String refCode);
}