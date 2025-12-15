package com.werp.sero.auth.controller;

import com.werp.sero.auth.dto.LoginRequestDTO;
import com.werp.sero.auth.dto.LoginResponseDTO;
import com.werp.sero.auth.service.AuthService;
import com.werp.sero.security.dto.enums.Type;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
public class AuthController {
    private final AuthService authService;

    @PostMapping("/auth/login")
    public ResponseEntity<LoginResponseDTO> loginByEmployee(@Valid @RequestBody final LoginRequestDTO request,
                                                            final HttpServletResponse response) {
        final LoginResponseDTO responseDto = authService.login(request, response, Type.EMPLOYEE);

        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/clients/auth/login")
    public ResponseEntity<LoginResponseDTO> loginByClientEmployee(@Valid @RequestBody final LoginRequestDTO request,
                                                                  final HttpServletResponse response) {
        final LoginResponseDTO responseDto = authService.login(request, response, Type.CLIENT_EMPLOYEE);

        return ResponseEntity.ok(responseDto);
    }
}