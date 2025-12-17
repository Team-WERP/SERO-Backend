package com.werp.sero.employee.query.service;


import com.werp.sero.employee.command.domain.aggregate.Department;
import com.werp.sero.employee.command.domain.aggregate.Employee;
import com.werp.sero.employee.query.dao.DepartmentMapper;
import com.werp.sero.employee.query.dao.EmployeeMapper;
import com.werp.sero.employee.query.dto.DepartmentWithEmployeesDTO;
import com.werp.sero.employee.query.dto.EmployeeListResponseDTO;
import com.werp.sero.employee.query.dto.OrganizationResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EmployeeQueryServiceImpl implements EmployeeQueryService {

    private final EmployeeMapper employeeMapper;
    private final DepartmentMapper departmentMapper;

    @Override
    public OrganizationResponseDTO getOrganization() {
        // 1. 모든 부서 조회
        List<Department> departments = departmentMapper.findAll();

        // 2. 각 부서별 사원 목록 조회 및 DTO 변환
        List<DepartmentWithEmployeesDTO> departmentDTOs = departments.stream()
                .map(department -> {
                    List<Employee> employees = employeeMapper.findByDepartmentIdWithDepartment(department.getId());
                    List<EmployeeListResponseDTO> employeeDTOs = employees.stream()
                            .map(EmployeeListResponseDTO::from)
                            .collect(Collectors.toList());
                    return DepartmentWithEmployeesDTO.of(department, employeeDTOs);
                })
                .collect(Collectors.toList());

        // 3. 조직도 DTO 생성 및 반환
        return OrganizationResponseDTO.of("(주)SERO", departmentDTOs);
    }

    @Override
    public DepartmentWithEmployeesDTO getDepartmentEmployees(int departmentId) {
        // 1. 부서 조회
        Department department = departmentMapper.findById(departmentId)
                .orElseThrow(() -> new IllegalArgumentException("부서를 찾을 수 없습니다."));

        // 2. 해당 부서의 사원 목록 조회
        List<Employee> employees = employeeMapper.findByDepartmentIdWithDepartment(departmentId);

        // 3. DTO 변환
        List<EmployeeListResponseDTO> employeeDTOs = employees.stream()
                .map(EmployeeListResponseDTO::from)
                .collect(Collectors.toList());

        // 4. 부서 정보 및 사원 목록 반환
        return DepartmentWithEmployeesDTO.of(department, employeeDTOs);
    }

    @Override
    public DepartmentWithEmployeesDTO getDepartmentEmployeesByName(String departmentName) {
        // 1. 부서명으로 부서 조회
        Department department = departmentMapper.findByName(departmentName)
                .orElseThrow(() -> new IllegalArgumentException("부서를 찾을 수 없습니다."));

        // 2. 해당 부서의 사원 목록 조회
        List<Employee> employees = employeeMapper.findByDepartmentIdWithDepartment(department.getId());

        // 3. DTO 변환
        List<EmployeeListResponseDTO> employeeDTOs = employees.stream()
                .map(EmployeeListResponseDTO::from)
                .collect(Collectors.toList());

        // 4. 부서 정보 및 사원 목록 반환
        return DepartmentWithEmployeesDTO.of(department, employeeDTOs);
    }
}
