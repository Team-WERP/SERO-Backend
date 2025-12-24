package com.werp.sero.approval.command.domain.repository;

import com.werp.sero.approval.command.domain.aggregate.Approval;
import com.werp.sero.approval.command.domain.aggregate.ApprovalLine;
import com.werp.sero.employee.command.domain.aggregate.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApprovalLineRepository extends JpaRepository<ApprovalLine, Integer> {
    boolean existsByApprovalAndSequenceIsNotNullAndSequenceGreaterThan(final Approval approval, final int sequence);

    Optional<ApprovalLine> findByApprovalAndEmployee(final Approval approval, final Employee employee);

    Optional<ApprovalLine> findFirstByApprovalAndSequenceGreaterThanOrderBySequenceAsc(Approval approval, int sequence);
}