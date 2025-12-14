package com.werp.sero.order.repository;

import com.werp.sero.employee.entity.Employee;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    @Query("SELECT e FROM Employee e JOIN FETCH e.department d WHERE d.deptCode = :deptCode")
    List<Employee> findByDepartmentDeptCode(@Param("deptCode") String deptCode);
}
