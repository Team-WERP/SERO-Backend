package com.werp.sero.notification.query.controller;

import com.werp.sero.employee.command.domain.aggregate.Employee;
import com.werp.sero.notification.query.dto.NotificationResponse;
import com.werp.sero.notification.query.service.NotificationQueryService;
import com.werp.sero.security.annotation.CurrentUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "알림 조회", description = "알림 목록 조회 및 읽음 처리 API")
@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationQueryController {
    private final NotificationQueryService notificationQueryService;

    @Operation(summary = "내 알림 목록 조회", description = "로그인한 직원의 알림 목록을 최신순으로 조회 (최대 50개)")
    @GetMapping
    public ResponseEntity<List<NotificationResponse>> getMyNotifications(
            @CurrentUser final Employee employee) {
        return ResponseEntity.ok(notificationQueryService.getMyNotifications(employee.getId()));
    }

    @Operation(summary = "읽지 않은 알림 개수", description = "로그인한 직원의 읽지 않은 알림 개수 조회")
    @GetMapping("/unread-count")
    public ResponseEntity<Integer> getUnreadCount(@CurrentUser final Employee employee) {
        return ResponseEntity.ok(notificationQueryService.getUnreadCount(employee.getId()));
    }

    @Operation(summary = "알림 읽음 처리", description = "특정 알림을 읽음 상태로 변경")
    @PatchMapping("/{notificationId}/read")
    public ResponseEntity<Void> markAsRead(@PathVariable final int notificationId) {
        notificationQueryService.markAsRead(notificationId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "모든 알림 읽음 처리", description = "로그인한 직원의 모든 읽지 않은 알림을 읽음 상태로 변경")
    @PatchMapping("/read-all")
    public ResponseEntity<Void> markAllAsRead(@CurrentUser final Employee employee) {
        notificationQueryService.markAllAsRead(employee.getId());
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "알림 삭제", description = "특정 알림을 삭제")
    @DeleteMapping("/{notificationId}")
    public ResponseEntity<Void> deleteNotification(@PathVariable final int notificationId) {
        notificationQueryService.deleteNotification(notificationId);
        return ResponseEntity.ok().build();
    }
}
