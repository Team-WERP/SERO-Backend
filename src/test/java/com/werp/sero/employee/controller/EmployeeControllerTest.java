package com.werp.sero.employee.controller;

import com.werp.sero.employee.dto.DepartmentWithEmployeesDTO;
import com.werp.sero.employee.dto.EmployeeListResponseDTO;
import com.werp.sero.employee.dto.OrganizationResponseDTO;
import com.werp.sero.employee.service.EmployeeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmployeeController.class)
@DisplayName("EmployeeController 테스트")
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EmployeeService employeeService;

    @Test
    @DisplayName("조직도 조회 - 성공")
    void getOrganization_Success() throws Exception {
        // given
        List<DepartmentWithEmployeesDTO> departments = Arrays.asList(
                createDepartmentWithEmployees(1, "AC_SAL", "영업팀", 2),
                createDepartmentWithEmployees(2, "AC_PRO", "생산팀", 2),
                createDepartmentWithEmployees(3, "AC_WHS", "물류팀", 2)
        );

        OrganizationResponseDTO response = OrganizationResponseDTO.builder()
                .companyName("(주)SERO")
                .departments(departments)
                .build();

        given(employeeService.getOrganization()).willReturn(response);

        // when & then
        mockMvc.perform(get("/api/employees/organization"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.companyName").value("(주)SERO"))
                .andExpect(jsonPath("$.departments").isArray())
                .andExpect(jsonPath("$.departments.length()").value(3))
                .andExpect(jsonPath("$.departments[0].id").value(1))
                .andExpect(jsonPath("$.departments[0].deptName").value("영업팀"))
                .andExpect(jsonPath("$.departments[0].deptCode").value("AC_SAL"))
                .andExpect(jsonPath("$.departments[0].employeeCount").value(2))
                .andExpect(jsonPath("$.departments[0].employees.length()").value(2))
                .andExpect(jsonPath("$.departments[1].id").value(2))
                .andExpect(jsonPath("$.departments[1].deptName").value("생산팀"));
    }

    @Test
    @DisplayName("부서별 사원 목록 조회 - 성공")
    void getDepartmentEmployees_Success() throws Exception {
        // given
        int departmentId = 1;
        DepartmentWithEmployeesDTO response = createDepartmentWithEmployees(1, "AC_SAL", "영업팀", 3);

        given(employeeService.getDepartmentEmployees(departmentId)).willReturn(response);

        // when & then
        mockMvc.perform(get("/api/employees/departments/{departmentId}", departmentId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.deptCode").value("AC_SAL"))
                .andExpect(jsonPath("$.deptName").value("영업팀"))
                .andExpect(jsonPath("$.employeeCount").value(3))
                .andExpect(jsonPath("$.employees").isArray())
                .andExpect(jsonPath("$.employees.length()").value(3))
                .andExpect(jsonPath("$.employees[0].name").exists())
                .andExpect(jsonPath("$.employees[0].positionCode").exists())
                .andExpect(jsonPath("$.employees[0].email").exists());
    }

    @Test
    @DisplayName("부서별 사원 목록 조회 - 빈 리스트")
    void getDepartmentEmployees_EmptyList() throws Exception {
        // given
        int departmentId = 10;
        DepartmentWithEmployeesDTO response = DepartmentWithEmployeesDTO.builder()
                .id(departmentId)
                .deptCode("AC_TEST")
                .deptName("테스트팀")
                .employeeCount(0)
                .employees(List.of())
                .build();

        given(employeeService.getDepartmentEmployees(departmentId)).willReturn(response);

        // when & then
        mockMvc.perform(get("/api/employees/departments/{departmentId}", departmentId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(departmentId))
                .andExpect(jsonPath("$.employeeCount").value(0))
                .andExpect(jsonPath("$.employees").isArray())
                .andExpect(jsonPath("$.employees.length()").value(0));
    }

    // 테스트용 DepartmentWithEmployeesDTO 생성
    private DepartmentWithEmployeesDTO createDepartmentWithEmployees(int deptId, String deptCode, String deptName, int employeeCount) {
        List<EmployeeListResponseDTO> employees = new java.util.ArrayList<>();
        for (int i = 1; i <= employeeCount; i++) {
            employees.add(EmployeeListResponseDTO.builder()
                    .id(deptId * 10 + i)
                    .name(deptName + " 사원" + i)
                    .positionCode("POS_EMP")
                    .rankCode("RANK_STAFF")
                    .email("employee" + i + "@sero.com")
                    .contact("010-1234-" + String.format("%04d", i))
                    .build());
        }

        return DepartmentWithEmployeesDTO.builder()
                .id(deptId)
                .deptCode(deptCode)
                .deptName(deptName)
                .employeeCount(employees.size())
                .employees(employees)
                .build();
    }
}
