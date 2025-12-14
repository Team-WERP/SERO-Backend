package com.werp.sero.employee.service;

import com.werp.sero.employee.dto.DepartmentWithEmployeesDTO;
import com.werp.sero.employee.dto.OrganizationResponseDTO;
import com.werp.sero.employee.entity.Department;
import com.werp.sero.employee.entity.Employee;
import com.werp.sero.employee.repository.DepartmentRepository;
import com.werp.sero.employee.repository.EmployeeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("EmployeeServiceImpl 테스트")
class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Test
    @DisplayName("조직도 조회 - 성공")
    void getOrganization_Success() {
        // given
        Department dept1 = createMockDepartment(1, "AC_SAL", "영업팀");
        Department dept2 = createMockDepartment(2, "AC_PRO", "생산팀");
        Department dept3 = createMockDepartment(3, "AC_WHS", "물류팀");

        List<Department> departments = Arrays.asList(dept1, dept2, dept3);

        Employee emp1 = createMockEmployee(11, "김영업", "POS_MGR", "RANK_MANAGER", "kim@sero.com", "010-1111-1111", dept1);
        Employee emp2 = createMockEmployee(12, "이영업", "POS_EMP", "RANK_STAFF", "lee@sero.com", "010-1111-2222", dept1);
        Employee emp3 = createMockEmployee(21, "박생산", "POS_MGR", "RANK_MANAGER", "park@sero.com", "010-2222-1111", dept2);
        Employee emp4 = createMockEmployee(31, "최물류", "POS_EMP", "RANK_STAFF", "choi@sero.com", "010-3333-1111", dept3);

        given(departmentRepository.findAll()).willReturn(departments);
        given(employeeRepository.findByDepartmentIdWithDepartment(1)).willReturn(Arrays.asList(emp1, emp2));
        given(employeeRepository.findByDepartmentIdWithDepartment(2)).willReturn(Arrays.asList(emp3));
        given(employeeRepository.findByDepartmentIdWithDepartment(3)).willReturn(Arrays.asList(emp4));

        // when
        OrganizationResponseDTO result = employeeService.getOrganization();

        // then
        assertThat(result).isNotNull();
        assertThat(result.getCompanyName()).isEqualTo("(주)SERO");
        assertThat(result.getDepartments()).hasSize(3);

        // 영업팀 확인
        DepartmentWithEmployeesDTO salesDept = result.getDepartments().get(0);
        assertThat(salesDept.getId()).isEqualTo(1);
        assertThat(salesDept.getDeptCode()).isEqualTo("AC_SAL");
        assertThat(salesDept.getDeptName()).isEqualTo("영업팀");
        assertThat(salesDept.getEmployeeCount()).isEqualTo(2);
        assertThat(salesDept.getEmployees()).hasSize(2);

        // 생산팀 확인
        DepartmentWithEmployeesDTO productionDept = result.getDepartments().get(1);
        assertThat(productionDept.getId()).isEqualTo(2);
        assertThat(productionDept.getDeptCode()).isEqualTo("AC_PRO");
        assertThat(productionDept.getEmployeeCount()).isEqualTo(1);

        // 물류팀 확인
        DepartmentWithEmployeesDTO logisticsDept = result.getDepartments().get(2);
        assertThat(logisticsDept.getId()).isEqualTo(3);
        assertThat(logisticsDept.getDeptCode()).isEqualTo("AC_WHS");
        assertThat(logisticsDept.getEmployeeCount()).isEqualTo(1);
    }

    @Test
    @DisplayName("조직도 조회 - 사원이 없는 부서 포함")
    void getOrganization_WithEmptyDepartment() {
        // given
        Department dept1 = createMockDepartment(1, "AC_SAL", "영업팀");

        List<Department> departments = Arrays.asList(dept1);

        given(departmentRepository.findAll()).willReturn(departments);
        given(employeeRepository.findByDepartmentIdWithDepartment(1)).willReturn(List.of());

        // when
        OrganizationResponseDTO result = employeeService.getOrganization();

        // then
        assertThat(result).isNotNull();
        assertThat(result.getDepartments()).hasSize(1);
        assertThat(result.getDepartments().get(0).getEmployeeCount()).isEqualTo(0);
        assertThat(result.getDepartments().get(0).getEmployees()).isEmpty();
    }

    @Test
    @DisplayName("부서별 사원 목록 조회 - 성공")
    void getDepartmentEmployees_Success() {
        // given
        int departmentId = 1;
        Department department = createMockDepartment(1, "AC_SAL", "영업팀");

        Employee emp1 = createMockEmployee(11, "김영업", "POS_MGR", "RANK_MANAGER", "kim@sero.com", "010-1111-1111", department);
        Employee emp2 = createMockEmployee(12, "이영업", "POS_EMP", "RANK_STAFF", "lee@sero.com", "010-1111-2222", department);

        List<Employee> employees = Arrays.asList(emp1, emp2);

        given(departmentRepository.findById(departmentId)).willReturn(Optional.of(department));
        given(employeeRepository.findByDepartmentIdWithDepartment(departmentId)).willReturn(employees);

        // when
        DepartmentWithEmployeesDTO result = employeeService.getDepartmentEmployees(departmentId);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1);
        assertThat(result.getDeptCode()).isEqualTo("AC_SAL");
        assertThat(result.getDeptName()).isEqualTo("영업팀");
        assertThat(result.getEmployeeCount()).isEqualTo(2);
        assertThat(result.getEmployees()).hasSize(2);
        assertThat(result.getEmployees().get(0).getName()).isEqualTo("김영업");
        assertThat(result.getEmployees().get(0).getPositionCode()).isEqualTo("POS_MGR");
        assertThat(result.getEmployees().get(1).getName()).isEqualTo("이영업");
    }

    @Test
    @DisplayName("부서별 사원 목록 조회 - 존재하지 않는 부서")
    void getDepartmentEmployees_DepartmentNotFound() {
        // given
        int departmentId = 999;
        given(departmentRepository.findById(departmentId)).willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> employeeService.getDepartmentEmployees(departmentId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("부서를 찾을 수 없습니다.");
    }

    @Test
    @DisplayName("부서별 사원 목록 조회 - 사원이 없는 부서")
    void getDepartmentEmployees_EmptyEmployeeList() {
        // given
        int departmentId = 1;
        Department department = createMockDepartment(1, "AC_SAL", "영업팀");

        given(departmentRepository.findById(departmentId)).willReturn(Optional.of(department));
        given(employeeRepository.findByDepartmentIdWithDepartment(departmentId)).willReturn(List.of());

        // when
        DepartmentWithEmployeesDTO result = employeeService.getDepartmentEmployees(departmentId);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1);
        assertThat(result.getDeptCode()).isEqualTo("AC_SAL");
        assertThat(result.getDeptName()).isEqualTo("영업팀");
        assertThat(result.getEmployeeCount()).isEqualTo(0);
        assertThat(result.getEmployees()).isEmpty();
    }

    // Mock Department 객체 생성
    private Department createMockDepartment(int id, String deptCode, String deptName) {
        Department department = mock(Department.class);
        lenient().when(department.getId()).thenReturn(id);
        lenient().when(department.getDeptCode()).thenReturn(deptCode);
        lenient().when(department.getDeptName()).thenReturn(deptName);
        return department;
    }

    // Mock Employee 객체 생성
    private Employee createMockEmployee(int id, String name, String positionCode, String rankCode,
                                        String email, String contact, Department department) {
        Employee employee = mock(Employee.class);
        lenient().when(employee.getId()).thenReturn(id);
        lenient().when(employee.getName()).thenReturn(name);
        lenient().when(employee.getPositionCode()).thenReturn(positionCode);
        lenient().when(employee.getRankCode()).thenReturn(rankCode);
        lenient().when(employee.getEmail()).thenReturn(email);
        lenient().when(employee.getContact()).thenReturn(contact);
        lenient().when(employee.getDepartment()).thenReturn(department);
        return employee;
    }
}
