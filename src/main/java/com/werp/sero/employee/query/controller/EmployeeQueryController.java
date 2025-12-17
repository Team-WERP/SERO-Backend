package com.werp.sero.employee.query.controller;


import com.werp.sero.common.security.AccessType;
import com.werp.sero.common.security.RequirePermission;
import com.werp.sero.employee.query.dto.DepartmentWithEmployeesDTO;
import com.werp.sero.employee.query.dto.OrganizationResponseDTO;
import com.werp.sero.employee.query.service.EmployeeQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@Tag(name = "조직/인사 관리", description = "조직도, 부서별 사원 목록 조회 API")
@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeQueryController {

    private final EmployeeQueryService employeeQueryService;

    /**
     * 조직도 조회 (전체 부서 및 사원 목록)
     *
     * GET /employees/organization
     *
     * @return 회사명, 부서 목록, 각 부서별 사원 목록 (부서코드, 부서명, 사원수, 사원 정보 포함)
     */
    @Operation(
            summary = "조직도 조회",
            description = "회사 전체 조직도를 조회합니다. 모든 부서와 각 부서에 소속된 사원 목록을 반환합니다."
    )
    @GetMapping("/organization")
    @RequirePermission(menu = "MM_EMP", authorities = {"AC_SYS", "AC_SAL", "AC_PRO", "AC_WHS"}, accessType = AccessType.READ)
    public OrganizationResponseDTO getOrganization() {
        return employeeQueryService.getOrganization();
    }

    /**
     * 부서별 사원 목록 조회 (부서 ID 기준)
     *
     * GET /employees/departments/{departmentId}
     *
     * @param departmentId 부서 ID
     * @return 부서 정보 (부서코드, 부서명, 사원수) 및 해당 부서의 사원 목록 (이름, 직급, 직책, 이메일, 연락처)
     */
    @Operation(
            summary = "부서별 사원 목록 조회 (부서 ID 기준)",
            description = "특정 부서에 소속된 모든 사원 목록을 조회합니다."
    )
    @GetMapping("/departments/{departmentId}")
    @RequirePermission(menu = "MM_EMP", authorities = {"AC_SYS", "AC_SAL", "AC_PRO", "AC_WHS"}, accessType = AccessType.READ)
    public DepartmentWithEmployeesDTO getDepartmentEmployees(
            @Parameter(description = "부서 ID", example = "1", required = true)
            @PathVariable int departmentId) {
        return employeeQueryService.getDepartmentEmployees(departmentId);
    }

    /**
     * 부서별 사원 목록 조회 (부서명 기준 - 부분 검색 지원)
     *
     * GET /employees/departments/name?deptName=영업
     *
     * @param deptName 부서명 (부분 검색 가능, 예: "영업" 입력 시 "영업1팀", "영업2팀", "영업부" 등 검색)
     * @return 부서 정보 (부서코드, 부서명, 사원수) 및 해당 부서의 사원 목록 (이름, 직급, 직책, 이메일, 연락처)
     */
    @Operation(
            summary = "부서별 사원 목록 조회 (부서명 부분 검색)",
            description = "부서명으로 특정 부서에 소속된 모든 사원 목록을 조회합니다. 부분 검색이 지원되며, 여러 부서가 매칭되는 경우 첫 번째 부서를 반환합니다."
    )
    @GetMapping("/departments/name")
    @RequirePermission(menu = "MM_EMP", authorities = {"AC_SYS", "AC_SAL", "AC_PRO", "AC_WHS"}, accessType = AccessType.READ)
    public DepartmentWithEmployeesDTO getDepartmentEmployeesByName(
            @Parameter(description = "부서명 (부분 검색 가능)", example = "영업", required = true)
            @RequestParam String deptName) {
        return employeeQueryService.getDepartmentEmployeesByName(deptName);
    }
}
