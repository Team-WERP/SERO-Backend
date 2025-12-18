package com.werp.sero.util;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;

public class HeaderUtil {
    private static final String GRANT_TYPE_PREFIX = "Bearer ";

    public static String extractAccessTokenFromHeader(final HttpServletRequest request) {
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (header != null && header.startsWith(GRANT_TYPE_PREFIX)) {
            return header.substring(7);
        }

        return null;
    }
}