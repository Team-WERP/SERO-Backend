package com.werp.sero.employee.query.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.werp.sero.employee.command.domain.aggregate.Department;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Optional;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Getter
@Builder
public class DepartmentWithEmployeesDTO {

    @Schema(description = "부서 ID")
    private int id;

    @Schema(description = "부서 코드")
    private String deptCode;

    @Schema(description = "부서명")
    private String deptName;

    @Schema(description = "사원 수 (직접 소속 사원만 카운트)")
    private Integer employeeCount;

    @Schema(description = "부서 직원 목록")
    private List<EmployeeByDepartmentResponseDTO> employees;

    @Schema(description = "팀 목록 (하위 부서)")
    private List<DepartmentWithEmployeesDTO> teams;

    /**
     * 계층 구조용 DTO 생성 (develop 브랜치 기능)
     */
    public static DepartmentWithEmployeesDTO of(final Department department,
                                                final List<EmployeeByDepartmentResponseDTO> employees,
                                                final List<DepartmentWithEmployeesDTO> children) {

        return DepartmentWithEmployeesDTO.builder()
                .id(department.getId())
                .deptCode(department.getDeptCode())
                .deptName(department.getDeptName())
                .employees(Optional.ofNullable(employees).orElseGet(List::of))
                .teams(Optional.ofNullable(children).orElseGet(List::of))
                .build();
    }

    /**
     * 단일 부서 사원 목록용 DTO 생성 (feat/16 브랜치 기능)
     */
    public static DepartmentWithEmployeesDTO of(Department department, List<EmployeeListResponseDTO> employeeList) {
        return DepartmentWithEmployeesDTO.builder()
                .id(department.getId())
                .deptCode(department.getDeptCode())
                .deptName(department.getDeptName())
                .employeeCount(employeeList.size())
                .employees(employeeList.stream()
                        .map(emp -> new EmployeeByDepartmentResponseDTO(
                                emp.getId(),
                                "", // empCode는 EmployeeListResponseDTO에 없음
                                emp.getName(),
                                emp.getContact(),
                                emp.getPositionCode(),
                                emp.getRankCode(),
                                department.getDeptName()
                        ))
                        .toList())
                .build();
    }
}
