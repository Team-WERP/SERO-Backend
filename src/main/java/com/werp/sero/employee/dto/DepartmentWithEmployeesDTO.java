package com.werp.sero.employee.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.werp.sero.employee.entity.Department;
import com.werp.sero.employee.entity.Employee;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Value;

import java.util.List;
import java.util.stream.Collectors;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Value
public class DepartmentWithEmployeesDTO {

    @Schema(description = "부서 ID")
    int id;

    @Schema(description = "부서명")
    String deptName;

    @Schema(description = "부서 코드")
    String deptCode;

    @Schema(description = "부서 직원 목록")
    List<EmployeeByDepartmentResponseDTO> employees;

    @Schema(description = "팀 목록")
    List<DepartmentWithEmployeesDTO> teams;

    public DepartmentWithEmployeesDTO(int id,
                                      String deptName,
                                      String deptCode,
                                      List<Employee> directEmployees,
                                      List<DepartmentWithEmployeesDTO> teams) {

        this.id = id;
        this.deptName = deptName;
        this.deptCode = deptCode;

        this.employees = directEmployees.stream()
                .map(EmployeeByDepartmentResponseDTO::of)
                .collect(Collectors.toList());

        this.teams = teams;
    }

    public static DepartmentWithEmployeesDTO of(final Department department,
                                                final List<Employee> directEmployees,
                                                final List<DepartmentWithEmployeesDTO> children) {


        final List<Employee> safeEmployees = directEmployees != null ? directEmployees : List.of();
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