package com.werp.sero.employee.query.service;

import com.werp.sero.employee.command.domain.aggregate.Department;
import com.werp.sero.employee.command.domain.aggregate.Employee;
import com.werp.sero.employee.command.domain.repository.DepartmentRepository;
import com.werp.sero.employee.query.dao.EmployeeMapper;
import com.werp.sero.employee.query.dto.DepartmentWithEmployeesDTO;
import com.werp.sero.employee.query.dto.EmployeeByDepartmentResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class EmployeeQueryServiceImpl implements EmployeeQueryService {
    private final EmployeeMapper employeeMapper;
    private final DepartmentRepository departmentRepository;

    @Transactional(readOnly = true)
    @Override
    public List<DepartmentWithEmployeesDTO> findAllEmployeesByDeptCode(final String deptCode) {

        List<Department> allDepartments = departmentRepository.findAll();

        List<Employee> allEmployeesInHierarchy = employeeMapper.findAllEmployeesByDeptCode(deptCode);

        // 부서 ID를 키로, 소속 직원 목록을 값으로 하는 Map 생성
        Map<Integer, List<Employee>> employeesByDeptId = allEmployeesInHierarchy.stream()
                .collect(Collectors.groupingBy(e -> e.getDepartment().getId()));

        Map<Integer, List<Department>> childrenMap = allDepartments.stream()
                .filter(d -> d.getParentDepartment() != null)
                .collect(Collectors.groupingBy(d -> d.getParentDepartment().getId()));

        List<Department> rootDepartments;
        if (deptCode != null && !deptCode.isEmpty()) {
            // 특정 부서 코드 입력시
            rootDepartments = allDepartments.stream()
                    .filter(d -> d.getDeptCode().equals(deptCode))
                    .collect(Collectors.toList());
        } else {
            rootDepartments = allDepartments.stream()
                    .filter(d -> d.getParentDepartment() == null)
                    .collect(Collectors.toList());
        }

        return buildHierarchy(rootDepartments, employeesByDeptId, childrenMap);
    }


    private List<DepartmentWithEmployeesDTO> buildHierarchy(
            List<Department> departments,
            Map<Integer, List<Employee>> employeesByDeptId,
            Map<Integer, List<Department>> childrenMap) {

        List<DepartmentWithEmployeesDTO> result = new ArrayList<>();

        for (Department dept : departments) {
            int currentDeptId = dept.getId();

            List<Employee> directEmployees = employeesByDeptId.getOrDefault(currentDeptId, List.of());

            List<Department> childDepartments = childrenMap.getOrDefault(currentDeptId, List.of());

            // 하위 부서 존재시, 재귀적으로 buildHierarchy를 호출하여 하위 구조 생성
            List<DepartmentWithEmployeesDTO> childrenDtos = List.of();
            if (!childDepartments.isEmpty()) {
                childrenDtos = buildHierarchy(childDepartments, employeesByDeptId, childrenMap);
            }

            List<EmployeeByDepartmentResponseDTO> employeeDtos = directEmployees.stream()
                    .map(EmployeeByDepartmentResponseDTO::of)
                    .collect(Collectors.toList());

            DepartmentWithEmployeesDTO dto = DepartmentWithEmployeesDTO.of(
                    dept,
                    employeeDtos,
                    childrenDtos
            );

            result.add(dto);
        }

        return result;
    }
}