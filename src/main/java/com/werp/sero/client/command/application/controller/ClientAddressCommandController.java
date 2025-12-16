package com.werp.sero.client.command.application.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "고객사 배송지 관련 API", description = "고객사 배송지 관련 API")
@RestController
@RequestMapping("clients/{clientId}")
@RequiredArgsConstructor
public class ClientAddressCommandController {
    
    @PostMapping
    public ResponseEntity<ClientAddressCreateResponse> createAddress(
        
    )
}
