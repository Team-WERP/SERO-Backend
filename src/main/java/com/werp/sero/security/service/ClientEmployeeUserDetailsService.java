package com.werp.sero.security.service;

import com.werp.sero.client.entity.ClientEmployee;
import com.werp.sero.client.exception.ClientEmployeeNotFoundException;
import com.werp.sero.client.repository.ClientEmployeeRepository;
import com.werp.sero.security.dto.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ClientEmployeeUserDetailsService implements UserDetailsService {
    private final ClientEmployeeRepository clientEmployeeRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final ClientEmployee clientEmployee = clientEmployeeRepository.findByEmail(username)
                .orElseThrow(ClientEmployeeNotFoundException::new);

        return new CustomUserDetails(clientEmployee, List.of("AC_CLI"));
    }
}