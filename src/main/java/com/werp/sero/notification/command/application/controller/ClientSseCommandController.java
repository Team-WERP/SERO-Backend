package com.werp.sero.notification.command.application.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.werp.sero.employee.command.domain.aggregate.ClientEmployee;
import com.werp.sero.notification.command.application.service.SseCommandService;
import com.werp.sero.security.annotation.CurrentUser;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "고객사 SSE 알림" , description = "고객사 실시간 알림 연결 API")
@RestController
@RequestMapping("/clients/notifications")
@RequiredArgsConstructor
public class ClientSseCommandController {

    private final SseCommandService sseCommandService;

    // 연결 하면 SSE 스트림 생성. JWT 인증은 기존 필터로 처리
    @Operation(summary = "SSE 연결", description = "고객사 직원이 실시간 알림을 받기 위한 SSE 연결")
    @GetMapping(value = "/sse/connect", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter connect(@CurrentUser final ClientEmployee clientEmployee) {
        return sseCommandService.createEmitterForClient(clientEmployee.getId());

    }
}
