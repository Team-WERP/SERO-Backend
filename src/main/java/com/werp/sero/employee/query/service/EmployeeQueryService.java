package com.werp.sero.employee.query.service;

import com.werp.sero.employee.query.dto.DepartmentWithEmployeesDTO;

public interface EmployeeQueryService {

    /**
     * 부서별 사원 목록 조회 (부서 ID 기준)
     *
     * @param departmentId 부서 ID
     * @return 부서 정보 및 사원 목록
     */
    DepartmentWithEmployeesDTO getDepartmentEmployees(int departmentId);

}
