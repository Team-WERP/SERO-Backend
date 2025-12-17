package com.werp.sero.auth.service;

import com.werp.sero.auth.dto.LoginRequestDTO;
import com.werp.sero.auth.dto.LoginResponseDTO;
import com.werp.sero.security.enums.Type;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {
    LoginResponseDTO login(final LoginRequestDTO requestDTO, final HttpServletResponse response, final Type type);
}