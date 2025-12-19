package com.werp.sero.shipping.command.application.controller;

import com.werp.sero.shipping.command.application.dto.DOCreateRequestDTO;
import com.werp.sero.shipping.command.application.service.DeliveryOrderCommandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/delivery-orders")
@RequiredArgsConstructor
@Tag(name = "DeliveryOrder", description = "납품서 관리 API")
public class DeliveryOrderCommandController {

    private final DeliveryOrderCommandService deliveryOrderCommandService;

    @PostMapping
    @Operation(summary = "납품서 생성", description = "주문을 기반으로 납품서를 생성합니다.")
    public ResponseEntity<Map<String, String>> createDeliveryOrder(
            @Valid @RequestBody DOCreateRequestDTO requestDTO,
            @AuthenticationPrincipal Integer managerId
    ) {
        String doCode = deliveryOrderCommandService.createDeliveryOrder(requestDTO, managerId);

        Map<String, String> response = new HashMap<>();
        response.put("message", "납품서가 생성되었습니다.");
        response.put("doCode", doCode);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
