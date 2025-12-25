package com.werp.sero.notification.command.application.service;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface SseCommandService {

    SseEmitter createEmitter(int employeeId);
    void sendToUser(int employeeId, Object data);

}
