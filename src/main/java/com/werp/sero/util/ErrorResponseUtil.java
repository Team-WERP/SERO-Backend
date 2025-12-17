package com.werp.sero.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.werp.sero.common.error.ErrorCode;
import com.werp.sero.common.error.ErrorResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class ErrorResponseUtil {
    private final ObjectMapper objectMapper;

    public void setErrorResponse(final HttpServletResponse response,
                                  final ErrorCode errorCode) throws IOException {
        response.setStatus(errorCode.getStatus().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        final ErrorResponse errorResponse = new ErrorResponse(errorCode.getCode(), errorCode.getMessage());

        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
}