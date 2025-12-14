package com.werp.sero.client.controller;

import com.werp.sero.client.dto.ClientAddressResponseDTO;
import com.werp.sero.client.dto.ClientInfoResponseDTO;
import com.werp.sero.client.service.ClientService;
import com.werp.sero.common.security.AccessType;
import com.werp.sero.common.security.RequirePermission;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    @RequirePermission(menu = "MM_CORP", authorities = {"AC_SYS", "AC_SAL", "AC_PRO", "AC_WHS"}, accessType = AccessType.READ)
    public ClientInfoResponseDTO getClientInfo(@PathVariable int id) {
        return clientService.getClientInfo(id);
    }

    /**
     * 고객사 배송지 목록 조회
     * GET /api/clients/{id}/addresses
     *
     * @param id 고객사 ID
     * @return 배송지 목록 (기본 배송지가 맨 앞에 위치)
     */
    @GetMapping("/{id}/addresses")
    @RequirePermission(menu = "MM_CORP", authorities = {"AC_SYS", "AC_SAL", "AC_PRO", "AC_WHS"}, accessType = AccessType.READ)
    public List<ClientAddressResponseDTO> getClientAddresses(@PathVariable int id) {
        return clientService.getClientAddresses(id);
    }
}
