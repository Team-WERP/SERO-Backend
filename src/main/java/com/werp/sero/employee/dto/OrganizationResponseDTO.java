package com.werp.sero.employee.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class OrganizationResponseDTO {

    private String companyName;                         // 회사명
    private List<DepartmentWithEmployeesDTO> departments;  // 부서 목록

    /* 설명. 회사명과 부서 목록으로 조직도 생성 */
    public static OrganizationResponseDTO of(String companyName, List<DepartmentWithEmployeesDTO> departments) {
        return OrganizationResponseDTO.builder()
                .companyName(companyName)
                .departments(departments)
                .build();
    }
}
