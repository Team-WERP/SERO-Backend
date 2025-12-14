package com.werp.sero.auth.service;

import com.werp.sero.auth.dto.LoginRequestDTO;
import com.werp.sero.auth.dto.LoginResponseDTO;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {
    LoginResponseDTO login(final LoginRequestDTO request, final HttpServletResponse response);
}