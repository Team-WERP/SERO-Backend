package com.werp.sero.permission.repository;

import com.werp.sero.employee.entity.Employee;
import com.werp.sero.permission.entity.EmployeePermission;
import com.werp.sero.permission.entity.EmployeePermissionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeePermissionRepository extends JpaRepository<EmployeePermission, EmployeePermissionId> {
    @Query("select ep.id.permission.code from EmployeePermission ep where ep.id.employee = :employee")
    List<String> findPermissionCodeByEmployee(@Param("employee") final Employee employee);
}