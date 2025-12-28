package com.werp.sero.notification.query.dto;

import com.werp.sero.notification.command.domain.aggregate.enums.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationResponse {
    private int id;
    private String title;
    private String content;
    private NotificationType type;
    private Integer referenceId;
    private String redirectUrl;
    private boolean isRead;
    private String createdAt;
}
