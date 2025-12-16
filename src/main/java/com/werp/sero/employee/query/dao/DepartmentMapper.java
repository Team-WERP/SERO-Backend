package com.werp.sero.employee.query.dao;


import com.werp.sero.employee.command.domain.aggregate.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentMapper extends JpaRepository<Department, Integer> {
}
