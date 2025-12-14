package com.werp.sero.auth.service;

import com.werp.sero.auth.dto.LoginRequestDTO;
import com.werp.sero.auth.dto.LoginResponseDTO;
import com.werp.sero.auth.exception.LoginFailedException;
import com.werp.sero.security.dto.JwtToken;
import com.werp.sero.security.jwt.JwtTokenProvider;
import com.werp.sero.util.CookieUtil;
import com.werp.sero.util.RedisUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisUtil redisUtil;
    private final CookieUtil cookieUtil;

    private static final String REFRESH_TOKEN_PREFIX = "RT: ";

    @Override
    public LoginResponseDTO login(final LoginRequestDTO request, final HttpServletResponse response) {
        try {
            final UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());

            final Authentication authentication = authenticationManager.authenticate(authenticationToken);

            final JwtToken accessToken = jwtTokenProvider.generateAccessToken(authentication);

            final JwtToken refreshToken = jwtTokenProvider.generateRefreshToken(authentication);

            redisUtil.setData(REFRESH_TOKEN_PREFIX + request.getEmail(), refreshToken.getToken(),
                    refreshToken.getExpirationTime(), TimeUnit.MINUTES);

            response.addHeader("Authorization", accessToken.getToken());

            cookieUtil.generateRefreshTokenCookie(response, refreshToken);

            return new LoginResponseDTO(accessToken.getToken(), refreshToken.getToken(), accessToken.getAuthorities());
        } catch (BadCredentialsException e) {
            throw new LoginFailedException();
        }
    }
}