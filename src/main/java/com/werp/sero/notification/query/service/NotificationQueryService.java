package com.werp.sero.notification.query.service;

import com.werp.sero.notification.query.dto.NotificationResponse;
import java.util.List;

public interface NotificationQueryService {
    // 본사 직원용
    List<NotificationResponse> getMyNotifications(int employeeId);
    int getUnreadCount(int employeeId);
    void markAsRead(int notificationId);
    void markAllAsRead(int employeeId);
    void deleteNotification(int notificationId);

    // 고객사 직원용
    List<NotificationResponse> getMyNotificationsForClient(int clientEmployeeId);
    int getUnreadCountForClient(int clientEmployeeId);
    void markAllAsReadForClient(int clientEmployeeId);
}
