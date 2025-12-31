package com.werp.sero.notification.query.dao;

import com.werp.sero.notification.query.dto.NotificationResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface NotificationMapper {
    List<NotificationResponse> findByReceiverId(

    @Param("receiverId") int receiverId);
    int countUnreadByReceiverId(@Param("receiverId") int receiverId);
    void markAsRead(@Param("notificationId") int notificationId);
    void markAllAsRead(@Param("receiverId") int receiverId);
    void deleteNotification(@Param("notificationId") int notificationId);
}
