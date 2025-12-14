package com.werp.sero.employee.service;

import com.werp.sero.employee.dto.EmployeeByDepartmentResponseDTO;
import com.werp.sero.order.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;

    @Transactional(readOnly = true)
    @Override
    public List<EmployeeByDepartmentResponseDTO> findEmployeesByDeptCode(String deptCode) {
        return employeeRepository.findByDepartmentDeptCode(deptCode).stream()
                .map(EmployeeByDepartmentResponseDTO::of)
                .collect(Collectors.toList());
    }
}
