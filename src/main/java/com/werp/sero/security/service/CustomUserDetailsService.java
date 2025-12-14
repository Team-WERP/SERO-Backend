package com.werp.sero.security.service;

import com.werp.sero.client.entity.ClientEmployee;
import com.werp.sero.client.repository.ClientEmployeeRepository;
import com.werp.sero.employee.entity.Employee;
import com.werp.sero.employee.repository.EmployeeRepository;
import com.werp.sero.permission.repository.EmployeePermissionRepository;
import com.werp.sero.security.dto.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final EmployeeRepository employeeRepository;
    private final EmployeePermissionRepository employeePermissionRepository;
    private final ClientEmployeeRepository clientEmployeeRepository;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final Optional<Employee> employee = employeeRepository.findByEmailAndStatus(username, "ES_ACT");

        if (employee.isPresent()) {
            final List<String> permissions = employeePermissionRepository.findPermissionCodeByEmployee(employee.get());

            return new CustomUserDetails(employee.get(), permissions);
        }

        final Optional<ClientEmployee> clientEmployee = clientEmployeeRepository.findByEmail(username);

        if (clientEmployee.isPresent()) {
            return new CustomUserDetails(clientEmployee.get(), List.of("AC_CLI"));
        }

        throw new UsernameNotFoundException("User not found");
    }
}