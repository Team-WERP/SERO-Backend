package com.werp.sero.auth.controller;

import com.werp.sero.auth.dto.LoginRequestDTO;
import com.werp.sero.auth.dto.LoginResponseDTO;
import com.werp.sero.auth.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/auth")
@RequiredArgsConstructor
@RestController
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody final LoginRequestDTO request, final HttpServletResponse response) {
        final LoginResponseDTO responseDto = authService.login(request, response);

        return ResponseEntity.ok(responseDto);
    }
}