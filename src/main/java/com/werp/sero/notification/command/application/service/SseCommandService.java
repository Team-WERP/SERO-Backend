package com.werp.sero.notification.command.application.service;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface SseCommandService {
    // 본사 직원용
    SseEmitter createEmitter(int employeeId);
    void sendToUser(int employeeId, Object data);

    // 고객사 직원용
    SseEmitter createEmitterForClient(int clientEmployeeId);
    void sendToClient(int clientEmployeeId, Object data);
}
