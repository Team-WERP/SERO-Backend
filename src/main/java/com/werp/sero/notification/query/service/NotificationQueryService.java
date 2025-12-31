package com.werp.sero.notification.query.service;

import com.werp.sero.notification.query.dto.NotificationResponse;
import java.util.List;

public interface NotificationQueryService {
    List<NotificationResponse> getMyNotifications(int employeeId);
    int getUnreadCount(int employeeId);
    void markAsRead(int notificationId);
    void markAllAsRead(int employeeId);
    void deleteNotification(int notificationId);
}
