package com.werp.sero.client.query.controller;

import java.util.List;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.werp.sero.client.query.dto.ClientAddressResponseDTO;
import com.werp.sero.client.query.service.ClientAddressQueryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;


@Tag(name = "고객사 배송지 관련 API", description = "고객사 배송지 관련 API")
@RequestMapping("clients/{clientId}")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@RestController
public class ClientAddressQueryController {
    
    private final ClientAddressQueryService clientAddressQueryService;

    @Operation(summary = "고객사 배송지 조회")
    @GetMapping("/addresses")
    public ResponseEntity<List<ClientAddressResponseDTO>> getClientAddresses(
        
        @PathVariable int clientId
        //  만약 배송지 조회할때 필터링 필요하다면 추가
        
     ) {

        // service 호출
        List<ClientAddressResponseDTO> addresses = clientAddressQueryService.getClientAddresses(clientId);
        return ResponseEntity.ok(addresses);
     }
}
