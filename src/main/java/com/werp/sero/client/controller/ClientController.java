package com.werp.sero.client.controller;

import com.werp.sero.client.dto.ClientInfoResponseDTO;
import com.werp.sero.client.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    /**
     * 고객사 기본 정보 조회
     * GET /api/clients/{id}
     *
     * @param id 고객사 ID
     * @return 고객사 기본 정보 (회사명, 대표자명, 사업자등록번호, 대표전화, 주소, 업태/종목)
     */
    @GetMapping("/{id}")
    public ClientInfoResponseDTO getClientInfo(@PathVariable int id) {
        return clientService.getClientInfo(id);
    }
}
