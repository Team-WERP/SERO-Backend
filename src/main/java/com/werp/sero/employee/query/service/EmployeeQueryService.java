package com.werp.sero.employee.query.service;

import com.werp.sero.employee.query.dto.DepartmentWithEmployeesDTO;

import java.util.List;

public interface EmployeeQueryService {
    List<DepartmentWithEmployeesDTO> findAllEmployeesByDeptCode(final String deptCode);
}
