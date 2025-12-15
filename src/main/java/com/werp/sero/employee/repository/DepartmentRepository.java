package com.werp.sero.employee.repository;

import com.werp.sero.employee.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {
    List<Department> findAllByOrderByParentDeptIdAscIdAsc();
}
