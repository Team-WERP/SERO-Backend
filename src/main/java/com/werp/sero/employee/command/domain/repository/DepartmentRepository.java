package com.werp.sero.employee.command.domain.repository;

import com.werp.sero.employee.command.domain.aggregate.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {
}
