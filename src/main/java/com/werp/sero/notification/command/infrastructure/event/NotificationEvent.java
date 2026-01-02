package com.werp.sero.notification.command.infrastructure.event;

import com.werp.sero.notification.command.domain.aggregate.enums.NotificationType;

import lombok.Getter;

@Getter
public class NotificationEvent {
    private final NotificationType type;      // 타입
    private final String title;               // 제목
    private final String content;             // 내용
    private final Integer receiverId;         // 본사 직원 수신자 ID (고객사인 경우 null)
    private final Integer clientEmployeeId;   // 고객사 직원 수신자 ID (본사인 경우 null)
    private final String redirectUrl;         // 이동할 URL

    // 본사 직원용 생성자
    public NotificationEvent(NotificationType type, String title, String content, int receiverId, String redirectUrl) {
        this.type = type;
        this.title = title;
        this.content = content;
        this.receiverId = receiverId;
        this.clientEmployeeId = null;
        this.redirectUrl = redirectUrl;
    }

    // 고객사 직원용 생성자 (새로 추가)
    public static NotificationEvent forClient(NotificationType type, String title, String content, int clientEmployeeId, String redirectUrl) {
        return new NotificationEvent(type, title, content, null, clientEmployeeId, redirectUrl);
    }

    // 전체 생성자 (private)
    private NotificationEvent(NotificationType type, String title, String content, Integer receiverId, Integer clientEmployeeId, String redirectUrl) {
        this.type = type;
        this.title = title;
        this.content = content;
        this.receiverId = receiverId;
        this.clientEmployeeId = clientEmployeeId;
        this.redirectUrl = redirectUrl;
    }
}
