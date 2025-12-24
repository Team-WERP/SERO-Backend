package com.werp.sero.approval.command.domain.repository;

import com.werp.sero.approval.command.domain.aggregate.ApprovalTemplateLine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApprovalTemplateLineRepository extends JpaRepository<ApprovalTemplateLine, Integer> {
}