package com.werp.sero.approval.command.domain.repository;

import com.werp.sero.approval.command.domain.aggregate.ApprovalTemplate;
import com.werp.sero.employee.command.domain.aggregate.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApprovalTemplateRepository extends JpaRepository<ApprovalTemplate, Integer> {
    boolean existsByEmployeeAndName(final Employee employee, final String name);

    Optional<ApprovalTemplate> findByIdAndEmployee(final int id, final Employee employee);
}