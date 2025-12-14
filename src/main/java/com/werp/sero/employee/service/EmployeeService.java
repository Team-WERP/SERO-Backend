package com.werp.sero.employee.service;

import java.util.List;
import com.werp.sero.employee.dto.EmployeeByDepartmentResponseDTO;

public interface EmployeeService {
    List<EmployeeByDepartmentResponseDTO> findEmployeesByDeptCode(String deptCode);
}
