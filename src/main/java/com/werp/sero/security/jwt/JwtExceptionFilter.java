package com.werp.sero.security.jwt;

import com.werp.sero.common.error.ErrorCode;
import com.werp.sero.security.exception.ExpiredTokenException;
import com.werp.sero.security.exception.InvalidTokenException;
import com.werp.sero.util.ErrorResponseUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class JwtExceptionFilter extends OncePerRequestFilter {
    private final ErrorResponseUtil errorResponseUtil;

    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response,
                                    final FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (ExpiredTokenException | ExpiredJwtException e) {
            errorResponseUtil.setErrorResponse(response, ErrorCode.EXPIRED_TOKEN);
        } catch (InvalidTokenException | JwtException e) {
            errorResponseUtil.setErrorResponse(response, ErrorCode.INVALID_TOKEN);
        }
    }
}