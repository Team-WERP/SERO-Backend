package com.werp.sero.notification.query.service;

import com.werp.sero.common.error.exception.BusinessException;
import com.werp.sero.common.error.ErrorCode;
import com.werp.sero.notification.command.domain.aggregate.Notification;
import com.werp.sero.notification.command.domain.repository.NotificationRepository;
import com.werp.sero.notification.query.dao.NotificationMapper;
import com.werp.sero.notification.query.dto.NotificationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationQueryServiceImpl implements NotificationQueryService {
    private final NotificationMapper notificationMapper;
    private final NotificationRepository notificationRepository;

    @Override
    @Transactional(readOnly = true)
    public List<NotificationResponse> getMyNotifications(int employeeId) {
        return notificationMapper.findByReceiverId(employeeId);
    }

    @Override
    @Transactional(readOnly = true)
    public int getUnreadCount(int employeeId) {
        return notificationMapper.countUnreadByReceiverId(employeeId);
    }

    @Override
    @Transactional
    public void markAsRead(int notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ENTITY_NOT_FOUND));

        notification.read();
    }
}
