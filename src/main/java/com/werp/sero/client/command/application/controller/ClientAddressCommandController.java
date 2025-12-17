package com.werp.sero.client.command.application.controller;

import com.werp.sero.client.command.application.dto.ClientAddressCreateResponse;
import com.werp.sero.client.command.application.dto.ClientAddressUpdateRequest;
import com.werp.sero.client.command.application.dto.ClientAddressUpdateResponse;
import com.werp.sero.client.command.application.service.ClientAddressCommandService;
import lombok.AccessLevel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.werp.sero.client.command.application.dto.ClientAddressCreateRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "고객사 배송지 관련 API", description = "고객사 배송지 관련 API")
@RestController
@RequestMapping("clients/{clientId}")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ClientAddressCommandController {

    private final ClientAddressCommandService clientAddressCommandService;

    @Operation(summary = "신규 배송지 등록" , description = "모달창을 통해 신규 배송지 등록")
    @PostMapping("/addresses")
    public ResponseEntity<ClientAddressCreateResponse> createNewAddress(
        
        @PathVariable int clientId,
        @RequestBody ClientAddressCreateRequest request

    ) {
        ClientAddressCreateResponse response =
            clientAddressCommandService.createAddress(clientId,request);
        return ResponseEntity.ok(response);    
            
    }

    @Operation(summary = "배송지 수정" , description = "모달창을 통해 배송지 정보 수정")
    @PutMapping("/addresses/{addressId}")
    public ResponseEntity<ClientAddressUpdateResponse> updateAddress(

            @PathVariable int clientId,
            @PathVariable int addressId,
            @RequestBody ClientAddressUpdateRequest request

    ) {
        ClientAddressUpdateResponse response =
                clientAddressCommandService.updateAddress(clientId,addressId,request);
        return ResponseEntity.ok(response);
    }
}
