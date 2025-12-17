package com.werp.sero.employee.query.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.werp.sero.employee.command.domain.aggregate.Department;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Getter
public class DepartmentWithEmployeesDTO {

    @Schema(description = "부서 ID")
    private final int id;

    @Schema(description = "부서명")
    private final String deptName;

    @Schema(description = "부서 코드")
    private final String deptCode;

    @Schema(description = "부서 직원 목록")
    private final List<EmployeeByDepartmentResponseDTO> employees;

    @Schema(description = "팀 목록")
    private final List<DepartmentWithEmployeesDTO> teams;

    public DepartmentWithEmployeesDTO(int id,
                                      String deptName,
                                      String deptCode,
                                      List<EmployeeByDepartmentResponseDTO> directEmployees,
                                      List<DepartmentWithEmployeesDTO> teams) {

        this.id = id;
        this.deptName = deptName;
        this.deptCode = deptCode;
        this.employees = directEmployees;
        this.teams = teams;
    }

    public static DepartmentWithEmployeesDTO of(final Department department,
                                                final List<EmployeeByDepartmentResponseDTO> employees,
                                                final List<DepartmentWithEmployeesDTO> children) {

        final List<EmployeeByDepartmentResponseDTO> safeEmployees = employees != null ? employees : List.of();
        final List<DepartmentWithEmployeesDTO> safeChildren = children != null ? children : List.of();

        return new DepartmentWithEmployeesDTO(
                department.getId(),
                department.getDeptName(),
                department.getDeptCode(),
                safeEmployees,
                safeChildren
        );
    }
}