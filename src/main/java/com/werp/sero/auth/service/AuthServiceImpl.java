package com.werp.sero.auth.service;

import com.werp.sero.auth.dto.LoginRequestDTO;
import com.werp.sero.auth.dto.LoginResponseDTO;
import com.werp.sero.auth.exception.LoginFailedException;
import com.werp.sero.security.dto.JwtToken;
import com.werp.sero.security.dto.enums.Type;
import com.werp.sero.security.jwt.JwtTokenProvider;
import com.werp.sero.util.CookieUtil;
import com.werp.sero.util.RedisUtil;
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
    private final AuthenticationManager employeeAuthenticationManager;
    private final AuthenticationManager clientEmployeeAuthenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisUtil redisUtil;
    private final CookieUtil cookieUtil;

    @Autowired
    public AuthServiceImpl(final AuthenticationManager employeeAuthenticationManager,
                           @Qualifier("clientEmployeeAuthenticationManager") final AuthenticationManager clientEmployeeAuthenticationManager,
                           final JwtTokenProvider jwtTokenProvider, final RedisUtil redisUtil,
                           final CookieUtil cookieUtil) {
        this.employeeAuthenticationManager = employeeAuthenticationManager;
        this.clientEmployeeAuthenticationManager = clientEmployeeAuthenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.redisUtil = redisUtil;
        this.cookieUtil = cookieUtil;
    }

    private static final String REFRESH_TOKEN_PREFIX = "RT: ";

    @Transactional
    @Override
    public LoginResponseDTO login(final LoginRequestDTO request, final HttpServletResponse response,
                                  final Type type) {
        try {
            final UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());

            final Authentication authentication = authenticate(type, authenticationToken);

            final JwtToken accessToken = jwtTokenProvider.generateAccessToken(authentication);

            final JwtToken refreshToken = jwtTokenProvider.generateRefreshToken(authentication);

            redisUtil.setData(REFRESH_TOKEN_PREFIX + request.getEmail(), refreshToken.getToken(),
                    refreshToken.getExpirationTime(), TimeUnit.MINUTES);

            response.addHeader("Authorization", accessToken.getToken());

            cookieUtil.generateRefreshTokenCookie(response, refreshToken);

            return new LoginResponseDTO(accessToken.getToken(), refreshToken.getToken(), accessToken.getAuthorities());
        } catch (InternalAuthenticationServiceException | BadCredentialsException e) {
            throw new LoginFailedException();
        }
    }

    private Authentication authenticate(final Type type, final UsernamePasswordAuthenticationToken authenticationToken) {
        if (type.equals(Type.EMPLOYEE)) {
            return employeeAuthenticationManager.authenticate(authenticationToken);
        }

        return clientEmployeeAuthenticationManager.authenticate(authenticationToken);
    }
}