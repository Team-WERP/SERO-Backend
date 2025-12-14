package com.werp.sero.employee.repository;

import com.werp.sero.employee.entity.Department;
import com.werp.sero.employee.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    /* 설명. 부서별 사원 목록 조회 (부서 정보 포함) */
    @Query("SELECT e FROM Employee e JOIN FETCH e.department WHERE e.department.id = :departmentId")
    List<Employee> findByDepartmentIdWithDepartment(int departmentId);

    /* 설명. 전체 사원 목록 조회 (부서 정보 포함) */
    @Query("SELECT e FROM Employee e LEFT JOIN FETCH e.department")
    List<Employee> findAllWithDepartment();
}
