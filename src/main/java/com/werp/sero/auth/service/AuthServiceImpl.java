package com.werp.sero.auth.service;

import com.werp.sero.auth.dto.LoginRequestDTO;
import com.werp.sero.auth.dto.LoginResponseDTO;
import com.werp.sero.auth.exception.LoginFailedException;
import com.werp.sero.security.dto.JwtToken;
import com.werp.sero.security.enums.Type;
import com.werp.sero.security.jwt.JwtTokenProvider;
import com.werp.sero.util.CookieUtil;
import com.werp.sero.util.HeaderUtil;
import com.werp.sero.util.RedisUtil;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@Service
public class AuthServiceImpl implements AuthService {
    private static final String REFRESH_TOKEN_PREFIX = "RT: ";
    private static final String GRANT_TYPE = "Bearer";

    private final RedisUtil redisUtil;
    private final CookieUtil cookieUtil;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager employeeAuthenticationManager;
    private final AuthenticationManager clientEmployeeAuthenticationManager;

    @Autowired
    public AuthServiceImpl(final RedisUtil redisUtil, final CookieUtil cookieUtil,
                           final JwtTokenProvider jwtTokenProvider, final AuthenticationManager employeeAuthenticationManager,
                           @Qualifier("clientEmployeeAuthenticationManager") final AuthenticationManager clientEmployeeAuthenticationManager) {
        this.redisUtil = redisUtil;
        this.cookieUtil = cookieUtil;
        this.employeeAuthenticationManager = employeeAuthenticationManager;
        this.clientEmployeeAuthenticationManager = clientEmployeeAuthenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Transactional
    @Override
    public LoginResponseDTO login(final LoginRequestDTO requestDTO, final HttpServletResponse response,
                                  final Type type) {
        try {
            final UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(requestDTO.getEmail(), requestDTO.getPassword());

            final Authentication authentication = authenticate(type, authenticationToken);

            final JwtToken accessToken = jwtTokenProvider.generateAccessToken(authentication);

            final JwtToken refreshToken = jwtTokenProvider.generateRefreshToken(authentication);

            redisUtil.setData(REFRESH_TOKEN_PREFIX + requestDTO.getEmail(), refreshToken.getToken(),
                    refreshToken.getExpirationTime(), TimeUnit.MILLISECONDS);

            cookieUtil.generateRefreshTokenCookie(response, refreshToken);

            return new LoginResponseDTO(accessToken.getToken(), GRANT_TYPE,
                    accessToken.getAuthorities());
        } catch (InternalAuthenticationServiceException | BadCredentialsException e) {
            throw new LoginFailedException();
        }
    }

    @Transactional
    @Override
    public void logout(final HttpServletRequest request, final HttpServletResponse response) {
        try {
            final String accessToken = HeaderUtil.extractAccessTokenFromHeader(request);

            jwtTokenProvider.validateToken(accessToken);

            final String email = jwtTokenProvider.extractEmail(accessToken);

            final String refreshToken = redisUtil.getData(REFRESH_TOKEN_PREFIX + email);

            if (refreshToken != null) {
                redisUtil.deleteData(REFRESH_TOKEN_PREFIX + email);

                cookieUtil.deleteRefreshTokenCookie(response);
            }

            final long expirationTime = jwtTokenProvider.getExpirationTime(accessToken) - System.currentTimeMillis();

            redisUtil.setData(accessToken, "logout", expirationTime, TimeUnit.MILLISECONDS);
        } catch (JwtException e) {
            throw new JwtException(e.getMessage());
        }
    }

    private Authentication authenticate(final Type type,
                                        final UsernamePasswordAuthenticationToken authenticationToken) {
        if (type.equals(Type.EMPLOYEE)) {
            return employeeAuthenticationManager.authenticate(authenticationToken);
        }

        return clientEmployeeAuthenticationManager.authenticate(authenticationToken);
    }
}