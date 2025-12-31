package com.werp.sero.auth.service;

import com.werp.sero.auth.dto.LoginRequestDTO;
import com.werp.sero.auth.dto.LoginResponseDTO;
import com.werp.sero.security.enums.Type;
import com.werp.sero.security.principal.CustomUserDetails;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;

public interface AuthService {
    LoginResponseDTO login(final LoginRequestDTO requestDTO, final HttpServletResponse response, final Type type);

    void logout(final HttpServletRequest request, final HttpServletResponse response);

    LoginResponseDTO reissue(final CustomUserDetails customUserDetails, final String refreshToken,
                             final HttpServletResponse response);
}