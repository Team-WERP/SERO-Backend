package com.werp.sero.employee.command.domain.repository;

import com.werp.sero.employee.command.domain.aggregate.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 직원 Command Repository 인터페이스
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    /**
     * 직원 ID로 조회
     */
    Optional<Employee> findById(int id);
}
