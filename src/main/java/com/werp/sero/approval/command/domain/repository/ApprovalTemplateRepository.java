package com.werp.sero.approval.command.domain.repository;

import com.werp.sero.approval.command.domain.aggregate.ApprovalTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApprovalTemplateRepository extends JpaRepository<ApprovalTemplate, Integer> {
    boolean existsByName(String name);
}