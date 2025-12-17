package com.werp.sero.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final String GRANT_TYPE_PREFIX = "Bearer ";

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response,
                                    final FilterChain filterChain) throws ServletException, IOException {
        final String accessToken = extractAccessTokenFromHeader(request);

        if (accessToken != null) {
            jwtTokenProvider.validateToken(accessToken);

            final Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    private String extractAccessTokenFromHeader(final HttpServletRequest request) {
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (header != null && header.startsWith(GRANT_TYPE_PREFIX)) {
            return header.substring(7);
        }

        return null;
    }
}