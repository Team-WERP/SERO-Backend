package com.werp.sero.employee.controller;

import com.werp.sero.employee.dto.DepartmentWithEmployeesDTO;
import com.werp.sero.employee.dto.OrganizationResponseDTO;
import com.werp.sero.employee.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    /**
     * 조직도 조회 (전체 부서 및 사원 목록)
     * GET /api/employees/organization
     *
     * @return 회사명, 부서 목록, 각 부서별 사원 목록
     */
    @GetMapping("/organization")
    public OrganizationResponseDTO getOrganization() {
        return employeeService.getOrganization();
    }

    /**
     * 부서별 사원 목록 조회
     * GET /api/employees/departments/{departmentId}
     *
     * @param departmentId 부서 ID
     * @return 부서 정보 및 해당 부서의 사원 목록
     */
    @GetMapping("/departments/{departmentId}")
    public DepartmentWithEmployeesDTO getDepartmentEmployees(@PathVariable int departmentId) {
        return employeeService.getDepartmentEmployees(departmentId);
    }
}
