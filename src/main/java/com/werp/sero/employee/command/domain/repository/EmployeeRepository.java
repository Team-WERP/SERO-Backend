package com.werp.sero.employee.command.domain.repository;

import com.werp.sero.employee.command.domain.aggregate.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    @Query("SELECT e FROM Employee e LEFT JOIN FETCH e.department WHERE e.email = :email AND e.status = :status")
    Optional<Employee> findByEmailAndStatusWithFetchJoin(final String email, final String status);

    List<Employee> findByIdIn(final List<Integer> employeeIds);
}