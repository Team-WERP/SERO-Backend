package com.werp.sero.auth.controller;

import com.werp.sero.auth.dto.LoginRequestDTO;
import com.werp.sero.auth.dto.LoginResponseDTO;
import com.werp.sero.auth.service.AuthService;
import com.werp.sero.employee.command.domain.aggregate.ClientEmployee;
import com.werp.sero.employee.command.domain.aggregate.Department;
import com.werp.sero.employee.command.domain.aggregate.Employee;
import com.werp.sero.security.annotation.CurrentUser;
import com.werp.sero.security.enums.Type;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Auth - 인증")
@RequiredArgsConstructor
@RestController
public class AuthController {
    private final AuthService authService;

    @Operation(summary = "본사 직원용 로그인")
    @PostMapping("/auth/login")
    public ResponseEntity<LoginResponseDTO> loginEmployee(@Valid @RequestBody final LoginRequestDTO requestDTO,
                                                          final HttpServletResponse response) {
        final LoginResponseDTO responseDTO = authService.login(requestDTO, response, Type.EMPLOYEE);

        return ResponseEntity.ok(responseDTO);
    }

    @Operation(summary = "고객사 직원용 로그인")
    @PostMapping("/clients/auth/login")
    public ResponseEntity<LoginResponseDTO> loginClientEmployee(@Valid @RequestBody final LoginRequestDTO requestDTO,
                                                                final HttpServletResponse response) {
        final LoginResponseDTO responseDTO = authService.login(requestDTO, response, Type.CLIENT_EMPLOYEE);

        return ResponseEntity.ok(responseDTO);
    }

    @Operation(summary = "로그인한 본사 직원용 정보 확인 임시 API")
    @GetMapping("/me")
    public ResponseEntity<String> employeeTest(@CurrentUser final Employee employee) {
        final Department department = employee.getDepartment();

        if (department == null) {
            return ResponseEntity.ok("본사직원: " + employee.getEmail());
        } else {
            return ResponseEntity.ok("본사직원: " + employee.getDepartment().getDeptName() + " " + employee.getEmail());
        }
    }

    @Operation(summary = "로그인한 고객사 직원용 정보 확인 임시 API")
    @GetMapping("/clients/me")
    public ResponseEntity<String> testEmployee(@CurrentUser final ClientEmployee clientEmployee) {
        return ResponseEntity.ok("고객사 직원: " + clientEmployee.getClient().getCompanyName() + " " + clientEmployee.getEmail());
    }
}