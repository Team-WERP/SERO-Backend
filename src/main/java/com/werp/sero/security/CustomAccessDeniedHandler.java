package com.werp.sero.security;

import com.werp.sero.common.error.ErrorCode;
import com.werp.sero.util.ErrorResponseUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    private final ErrorResponseUtil errorResponseUtil;

    @Override
    public void handle(final HttpServletRequest request, final HttpServletResponse response,
                       final AccessDeniedException accessDeniedException) throws IOException, ServletException {
        errorResponseUtil.setErrorResponse(response, ErrorCode.FORBIDDEN);
    }
}