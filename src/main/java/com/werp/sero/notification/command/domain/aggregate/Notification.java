package com.werp.sero.notification.command.domain.aggregate;

import java.time.LocalDateTime;

import com.werp.sero.employee.command.domain.aggregate.Employee;
import com.werp.sero.notification.command.domain.aggregate.enums.NotificationType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Table(name = "notification")
@NoArgsConstructor(access = AccessLevel.PROTECTED) 
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Entity
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; 

    @Column(nullable = false)
    private String title; // '제목

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content; // 내용

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationType type; // 타입(주문 관련, 배송관련, 생산 관련 등등)

    @Column(name = "receiver_id", nullable = false)
    private int receiverId; // 알림을 받는 직원 ID (수신자)

    @Column(name = "reference_id")
    private int referenceId; // targetId (주문 PK, 결재 PK 등)

    @Column(name = "redirect_url")
    private String redirectUrl; // 이동할 URL

    @Column(name = "is_read", nullable = false)
    private boolean isRead = false; // 읽음 여부 (기본값 false)

    @Column(name = "created_at", nullable = false)
    private String createdAt; 

    public void read() {
        this.isRead = true;
    }

}

