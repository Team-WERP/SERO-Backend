package com.werp.sero.employee.command.domain.repository;

import com.werp.sero.employee.command.domain.aggregate.Employee;

import java.util.Optional;

/**
 * 직원 Command Repository 인터페이스
 */
public interface EmployeeRepository {

    /**
     * 직원 ID로 조회
     */
    Optional<Employee> findById(int id);
}
