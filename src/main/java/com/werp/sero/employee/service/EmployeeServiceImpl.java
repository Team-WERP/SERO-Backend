package com.werp.sero.employee.service;

import com.werp.sero.employee.dto.DepartmentWithEmployeesDTO;
import com.werp.sero.employee.entity.Department;
import com.werp.sero.employee.entity.Employee;
import com.werp.sero.employee.repository.DepartmentRepository;
import com.werp.sero.employee.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    @Transactional(readOnly = true)
    @Override
    public List<DepartmentWithEmployeesDTO> findEmployeesByDeptCode(String rootDeptCode) {

        List<Department> allDepartments = departmentRepository.findAllByOrderByParentDeptIdAscIdAsc();
        List<Employee> allEmployees = employeeRepository.findAllWithDepartment();

        Map<Integer, List<Employee>> employeesByDeptId = allEmployees.stream()
                .collect(Collectors.groupingBy(e -> e.getDepartment().getId()));

        Map<Integer, List<Department>> childrenMap = allDepartments.stream()
                .filter(d -> d.getParentDeptId() != null)
                .collect(Collectors.groupingBy(Department::getParentDeptId));

        List<Department> rootDepartments = allDepartments.stream()
                .filter(d -> d.getDeptCode().equals(rootDeptCode))
                .collect(Collectors.toList());

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

            List<DepartmentWithEmployeesDTO> childrenDtos = List.of();
            if (!childDepartments.isEmpty()) {
                childrenDtos = buildHierarchy(childDepartments, employeesByDeptId, childrenMap);
            }

            DepartmentWithEmployeesDTO dto = DepartmentWithEmployeesDTO.of(
                    dept,
                    directEmployees,
                    childrenDtos
            );

            result.add(dto);
        }

        return result;
    }
}