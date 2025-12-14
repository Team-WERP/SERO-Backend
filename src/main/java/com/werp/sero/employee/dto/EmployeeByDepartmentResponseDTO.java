package com.werp.sero.employee.dto;

import com.werp.sero.employee.entity.Employee;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Value;

@Value
public class EmployeeByDepartmentResponseDTO {

    @Schema(description = "직원 ID")
    int id;

    @Schema(description = "직원 코드")
    String empCode;

    @Schema(description = "직원 이름")
    String name;

    @Schema(description = "직원 연락처")
    String contact;

    @Schema(description = "직위 코드")
    String positionCode;

    @Schema(description = "직급 코드")
    String rankCode;

    @Schema(description = "부서 이름")
    String deptName;

    public static EmployeeByDepartmentResponseDTO of(final Employee employee) {

        final String departmentName = employee.getDepartment() != null
                ? employee.getDepartment().getDeptName()
                : "미배정";

        return new EmployeeByDepartmentResponseDTO(
                employee.getId(),
                employee.getEmpCode(),
                employee.getName(),
                employee.getContact(),
                employee.getPositionCode(),
                employee.getRankCode(),
                departmentName
        );
    }
}