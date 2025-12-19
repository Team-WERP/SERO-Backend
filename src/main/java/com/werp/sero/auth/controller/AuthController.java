package com.werp.sero.auth.controller;

import com.werp.sero.auth.dto.LoginRequestDTO;
import com.werp.sero.auth.dto.LoginResponseDTO;
import com.werp.sero.auth.service.AuthService;
import com.werp.sero.security.enums.Type;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "인증", description = "인증 관련 API")
@RequiredArgsConstructor
@RestController
public class AuthController {
    private final AuthService authService;

    @Operation(summary = "본사 직원용 로그인", description = "- email : admin@werp.com\n- password : kang")
    @PostMapping("/auth/login")
    public ResponseEntity<LoginResponseDTO> loginEmployee(@Valid @RequestBody final LoginRequestDTO requestDTO,
                                                          final HttpServletResponse response) {
        final LoginResponseDTO responseDTO = authService.login(requestDTO, response, Type.EMPLOYEE);

        return ResponseEntity.ok(responseDTO);
    }

    @Operation(summary = "고객사 직원용 로그인", description = "- email : sl_buyer@sl.com\n- password : kang")
    @PostMapping("/clients/auth/login")
    public ResponseEntity<LoginResponseDTO> loginClientEmployee(@Valid @RequestBody final LoginRequestDTO requestDTO,
                                                                final HttpServletResponse response) {
        final LoginResponseDTO responseDTO = authService.login(requestDTO, response, Type.CLIENT_EMPLOYEE);

        return ResponseEntity.ok(responseDTO);
    }

    @Operation(summary = "본사 직원용 로그아웃")
    @PostMapping("/auth/logout")
    public ResponseEntity<Void> logoutEmployee(final HttpServletRequest request, final HttpServletResponse response) {
        authService.logout(request, response);

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "고객사 직원용 로그아웃")
    @PostMapping("/clients/auth/logout")
    public ResponseEntity<Void> logoutClientEmployee(final HttpServletRequest request, final HttpServletResponse response) {
        authService.logout(request, response);

        return ResponseEntity.noContent().build();
    }
}