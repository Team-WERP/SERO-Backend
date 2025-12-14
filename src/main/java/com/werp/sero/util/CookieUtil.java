package com.werp.sero.util;

import com.werp.sero.security.dto.JwtToken;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@Component
public class CookieUtil {
    private final String COOKIE_NAME = "refreshToken";

    public void generateRefreshTokenCookie(final HttpServletResponse response, final JwtToken token) {
        ResponseCookie responseCookie = ResponseCookie.from(COOKIE_NAME, token.getToken())
                .httpOnly(true)
                .sameSite("None")
                .path("/")
                .secure(true)
                .maxAge(token.getExpirationTime() / 1000)
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, responseCookie.toString());
    }
}