package com.werp.sero.notification.command.domain.repository;


import com.werp.sero.notification.command.domain.aggregate.Notification;
import org.springframework.data.jpa.repository.JpaRepository;


public interface NotificationRepository extends JpaRepository<Notification, Integer>{
    
}
