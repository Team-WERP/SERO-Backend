package com.werp.sero.employee.query.dto;

import com.werp.sero.employee.command.domain.aggregate.Employee;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EmployeeListResponseDTO {

    private int id;
    private String name;           // 이름
    private String positionCode;   // 직위 코드
    private String rankCode;       // 직책 코드
    private String email;          // 이메일
    private String contact;        // 전화번호

    public static EmployeeListResponseDTO from(Employee employee) {
        return EmployeeListResponseDTO.builder()
                .id(employee.getId())
                .name(employee.getName())
                .positionCode(employee.getPositionCode())
                .rankCode(employee.getRankCode())
                .email(employee.getEmail())
                .contact(employee.getContact())
                .build();
    }
}
