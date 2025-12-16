package com.werp.sero.employee.command.infrastructure.repository;

import com.werp.sero.employee.command.domain.aggregate.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 직원 JPA Repository (Command용 - CUD)
 */
public interface JpaEmployeeRepository extends JpaRepository<Employee, Integer> {
}
