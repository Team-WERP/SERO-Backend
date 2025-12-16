package com.werp.sero.employee.command.infrastructure.service;

import com.werp.sero.employee.command.domain.aggregate.Employee;
import com.werp.sero.employee.command.domain.repository.EmployeeRepository;
import com.werp.sero.employee.command.infrastructure.repository.JpaEmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 직원 Repository 구현체 (JPA 활용)
 */
@Repository
@RequiredArgsConstructor
public class EmployeeRepositoryImpl implements EmployeeRepository {

    private final JpaEmployeeRepository jpaEmployeeRepository;

    @Override
    public Optional<Employee> findById(int id) {
        return jpaEmployeeRepository.findById(id);
    }
}
