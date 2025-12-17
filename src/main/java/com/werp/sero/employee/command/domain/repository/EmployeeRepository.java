package com.werp.sero.employee.command.domain.repository;

import com.werp.sero.employee.command.domain.aggregate.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    Optional<Employee> findByEmailAndStatus(final String email, final String status);
}