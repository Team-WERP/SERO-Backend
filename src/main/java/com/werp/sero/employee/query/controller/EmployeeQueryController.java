package com.werp.sero.employee.query.controller;


import com.werp.sero.common.security.AccessType;
import com.werp.sero.common.security.RequirePermission;
import com.werp.sero.employee.query.dto.DepartmentWithEmployeesDTO;
import com.werp.sero.employee.query.service.EmployeeQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Employee", description = "본사 직원 관련 API")
@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeQueryController {

    private final EmployeeQueryService employeeQueryService;

    /**
     * 부서별 사원 목록 조회 (부서 코드 기준)
     *
     * GET /employees
     *
     * @param deptCode 부서 코드 (선택적)
     * @return 부서별 사원 계층 구조
     */
    @Operation(summary = "부서별 본사 직원 목록 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "부서별 본사 직원 목록 조회", content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(
                            schema = @Schema(implementation = DepartmentWithEmployeesDTO.class)
                    )
            )),
            @ApiResponse(responseCode = "404", content = @Content(mediaType = "application/json", examples = {
                    @ExampleObject(name = "EMPLOYEE_LIST_NOT_FOUND", value = """
                            {
                                "code": "EMPLOYEE001",
                                "message": "Employee list not found"
                            }
                            """)
            }))
    })
    @GetMapping
    public ResponseEntity<List<DepartmentWithEmployeesDTO>> getEmployeesByDept(
            @RequestParam(value = "deptCode", required = false) final String deptCode) {

        final List<DepartmentWithEmployeesDTO> hierarchy =
                employeeQueryService.findAllEmployeesByDeptCode(deptCode);

        return ResponseEntity.ok(hierarchy);
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

}
