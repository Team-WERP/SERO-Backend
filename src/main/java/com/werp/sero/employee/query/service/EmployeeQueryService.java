package com.werp.sero.employee.query.service;

import com.werp.sero.employee.query.dto.DepartmentWithEmployeesDTO;

import java.util.List;

public interface EmployeeQueryService {

    /**
     * 부서 코드 기준으로 부서별 사원 계층 구조 조회
     *
     * @param deptCode 부서 코드 (null인 경우 전체 조직도 조회)
     * @return 부서별 사원 계층 구조 목록
     */
    List<DepartmentWithEmployeesDTO> findAllEmployeesByDeptCode(final String deptCode);

    /**
     * 부서별 사원 목록 조회 (부서 ID 기준)
     *
     * @param departmentId 부서 ID
     * @return 부서 정보 및 사원 목록
     */
    DepartmentWithEmployeesDTO getDepartmentEmployees(int departmentId);

}
