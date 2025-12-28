package com.werp.sero.notification.command.infrastructure.event;

import com.werp.sero.notification.command.domain.aggregate.enums.NotificationType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class NotificationEvent {
    private final NotificationType type;      // 타입
    private final String title;               // 제목
    private final String content;             // 내용
    private final int receiverId;             // 수신자
    private final String redirectUrl;         // 이동할 URL
}
