package com.werp.sero.employee.service;

import java.util.List;

import com.werp.sero.employee.dto.DepartmentWithEmployeesDTO;

public interface EmployeeService {
    List<DepartmentWithEmployeesDTO> findEmployeesByDeptCode(String deptCode);
}
