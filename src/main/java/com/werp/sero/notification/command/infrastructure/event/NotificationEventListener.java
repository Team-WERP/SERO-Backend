package com.werp.sero.notification.command.infrastructure.event;

import com.werp.sero.notification.command.application.service.SseCommandService;
import com.werp.sero.notification.command.domain.aggregate.Notification;
import com.werp.sero.notification.command.domain.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationEventListener {
    private final NotificationRepository notificationRepository;
    private final SseCommandService sseCommandService;

    @Async
    @EventListener
    @Transactional
    public void handleNotificationEvent(NotificationEvent event) {
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        // DB 저장
        Notification notification = Notification.builder()
                .type(event.getType())
                .title(event.getTitle())
                .content(event.getContent())
                .receiverId(event.getReceiverId())
                .redirectUrl(event.getRedirectUrl())
                .isRead(false)
                .createdAt(now)
                .build();
        
        notificationRepository.save(notification);

        // SSE 실시간 전송
        try {
            Map<String, Object> data = new HashMap<>();
            data.put("id", notification.getId());
            data.put("type", notification.getType());
            data.put("title", notification.getTitle());
            data.put("content", notification.getContent());
            data.put("redirectUrl", notification.getRedirectUrl());
            data.put("createdAt", notification.getCreatedAt());

            sseCommandService.sendToUser(event.getReceiverId(), data);
        } catch (Exception e) {
            log.error("SSE 전송 실패: receiverId={}", event.getReceiverId(), e);
        }
    }
}