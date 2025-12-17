package com.werp.sero.system.command.domain.repository;

import com.werp.sero.employee.command.domain.aggregate.Employee;
import com.werp.sero.permission.command.domain.aggregate.EmployeePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeePermissionRepository extends JpaRepository<EmployeePermission, Integer> {
    @Query("select ep.id.permission.code from EmployeePermission ep where ep.id.employee = :employee")
    List<String> findPermissionCodeByEmployee(@Param("employee") final Employee employee);
}