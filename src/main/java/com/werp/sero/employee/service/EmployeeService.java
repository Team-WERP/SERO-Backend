package com.werp.sero.employee.service;

import com.werp.sero.employee.dto.DepartmentWithEmployeesDTO;
import com.werp.sero.employee.dto.OrganizationResponseDTO;

public interface EmployeeService {

    /**
     * 조직도 조회 (전체 부서 및 사원 목록)
     *
     * @return 조직도 정보
     */
    OrganizationResponseDTO getOrganization();

    /**
     * 부서별 사원 목록 조회
     *
     * @param departmentId 부서 ID
     * @return 부서 정보 및 사원 목록
     */
    DepartmentWithEmployeesDTO getDepartmentEmployees(int departmentId);
}
