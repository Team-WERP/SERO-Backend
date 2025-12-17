package com.werp.sero.auth.controller;

import com.werp.sero.auth.dto.LoginRequestDTO;
import com.werp.sero.auth.dto.LoginResponseDTO;
import com.werp.sero.auth.service.AuthService;
import com.werp.sero.employee.command.domain.aggregate.ClientEmployee;
import com.werp.sero.employee.command.domain.aggregate.Employee;
import com.werp.sero.security.annotation.CurrentUser;
import com.werp.sero.security.dto.enums.Type;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class AuthController {
    private final AuthService authService;

    @PostMapping("/auth/login")
    public ResponseEntity<LoginResponseDTO> loginEmployee(@Valid @RequestBody final LoginRequestDTO requestDTO,
                                                          final HttpServletResponse response) {
        final LoginResponseDTO responseDTO = authService.login(requestDTO, response, Type.EMPLOYEE);

        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping("/clients/auth/login")
    public ResponseEntity<LoginResponseDTO> loginClientEmployee(@Valid @RequestBody final LoginRequestDTO requestDTO,
                                                                final HttpServletResponse response) {
        final LoginResponseDTO responseDTO = authService.login(requestDTO, response, Type.CLIENT_EMPLOYEE);

        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/me")
    public ResponseEntity<String> employeeTest(@CurrentUser final Employee employee) {
        return ResponseEntity.ok("본사직원: " + employee.getDepartment().getDeptName() + " " + employee.getEmail());
    }

    @GetMapping("/clients/me")
    public ResponseEntity<String> testEmployee(@CurrentUser final ClientEmployee clientEmployee) {
        return ResponseEntity.ok("고객사 직원: " + clientEmployee.getClient().getCompanyName() + " " + clientEmployee.getEmail());
    }
}