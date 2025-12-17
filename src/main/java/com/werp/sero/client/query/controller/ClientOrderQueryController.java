package com.werp.sero.client.query.controller;

import com.werp.sero.client.query.dto.ClientOrderResponseDTO;
import com.werp.sero.client.query.service.ClientMyOrderQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "고객사 나의 주문 조회 관련 API", description = "고객사 나의 주문 조회 관련 API")
@RequestMapping("clients/{clientId}")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@RestController
public class ClientOrderQueryController {

    private final ClientMyOrderQueryService clientMyOrderQueryService;

    @Operation(summary = "고객사 주문 목록 조회 (내가 한 주문 목록 조회)")
    @GetMapping("/orders")
    public ResponseEntity<List<ClientOrderResponseDTO>> getClientOrders(

            @PathVariable("clientId") String clientId) {
        
    }
}
