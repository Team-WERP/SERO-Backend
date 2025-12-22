package com.werp.sero.delivery.command.application.controller;


import com.werp.sero.delivery.command.application.service.DeliveryControllCommandService;
import com.werp.sero.employee.command.domain.aggregate.Employee;
import com.werp.sero.security.annotation.CurrentUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Tag(name = "배송 추적/관리 관련 API", description = "배송 추적/관리 관련 API")
@RestController
@RequestMapping("/delivery")
public class DeliveryControllCommandController {

    private final DeliveryControllCommandService deliveryControllCommandService;

    @PostMapping("/start")
    @Operation(summary = "배송 시작", description = "출고 완료된 배송을 '배송중' 상태로 변경합니다.")
    public ResponseEntity<Map<String, String>> startDelivery(
            @RequestParam String giCode,
            @CurrentUser Employee driver
    ) {
        deliveryControllCommandService.startDelivery(giCode,driver);

        Map<String, String> response = new HashMap<>();
        response.put("message", "배송이 시작되었습니다.");
        response.put("")

    }


}
