package com.werp.sero.employee.query.dto;

import com.werp.sero.employee.command.domain.aggregate.Department;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class DepartmentWithEmployeesDTO {

    private int id;
    private String deptCode;       // 부서 코드
    private String deptName;       // 부서명
    private int employeeCount;     // 사원 수
    private List<EmployeeListResponseDTO> employees;  // 사원 목록

    /* 설명. Entity와 사원 목록으로 DTO 생성 */
    public static DepartmentWithEmployeesDTO of(Department department, List<EmployeeListResponseDTO> employees) {
        return DepartmentWithEmployeesDTO.builder()
                .id(department.getId())
                .deptCode(department.getDeptCode())
                .deptName(department.getDeptName())
                .employeeCount(employees.size())
                .employees(employees)
                .build();
    }
}
