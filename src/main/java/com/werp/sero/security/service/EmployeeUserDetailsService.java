package com.werp.sero.security.service;

import com.werp.sero.employee.command.domain.aggregate.Employee;
import com.werp.sero.employee.command.domain.repository.EmployeeRepository;
import com.werp.sero.employee.command.exception.EmployeeNotFoundException;
import com.werp.sero.security.principal.CustomUserDetails;
import com.werp.sero.permission.command.domain.repository.EmployeePermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class EmployeeUserDetailsService implements UserDetailsService {
    private final EmployeeRepository employeeRepository;
    private final EmployeePermissionRepository employeePermissionRepository;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final Employee employee = employeeRepository.findByEmailAndStatusWithFetchJoin(username, "ES_ACT")
                .orElseThrow(EmployeeNotFoundException::new);

        final List<String> permissions = employeePermissionRepository.findPermissionCodeByEmployee(employee);

        return new CustomUserDetails(employee, permissions);
    }
}