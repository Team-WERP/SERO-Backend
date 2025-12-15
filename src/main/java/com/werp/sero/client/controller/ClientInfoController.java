package com.werp.sero.client.controller;

import com.werp.sero.client.dto.ClientAddressResponseDTO;
import com.werp.sero.client.dto.ClientInfoResponseDTO;
import com.werp.sero.client.service.ClientInfoService;
import com.werp.sero.common.security.AccessType;
import com.werp.sero.common.security.RequirePermission;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "고객사 정보 관리", description = "고객사 기본 정보, 배송지 목록 조회 API")
@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientInfoController {

    private final ClientInfoService clientInfoService;

    /**
     * 고객사 기본 정보 조회
     *
     * GET /clients/{id}
     *
     * @param id 고객사 ID
     * @return 고객사 기본 정보 (회사명, 대표자명, 사업자등록번호, 대표전화, 주소, 업태/종목)
     */
    @Operation(
            summary = "고객사 기본 정보 조회",
            description = "고객사 ID 기준으로 회사명, 대표자명, 사업자등록번호, 대표전화, 주소, 업태/종목 정보를 조회합니다."
    )
    @GetMapping("/{id}")
    @RequirePermission(menu = "MM_CORP", authorities = {"AC_SYS", "AC_SAL", "AC_PRO", "AC_WHS"}, accessType = AccessType.READ)
    public ClientInfoResponseDTO getClientInfo(
            @Parameter(description = "고객사 ID", example = "1", required = true)
            @PathVariable int id) {
        return clientInfoService.getClientInfo(id);
    }

    /**
     * 고객사 배송지 목록 조회
     *
     * GET /clients/{id}/addresses
     *
     * @param id 고객사 ID
     * @return 배송지 목록 (기본 배송지가 맨 앞에 위치, 배송지명, 주소, 수령인, 연락처 포함)
     */
    @Operation(
            summary = "고객사 배송지 목록 조회",
            description = "특정 고객사의 모든 배송지 목록을 조회합니다. 기본 배송지가 맨 앞에 정렬됩니다."
    )
    @GetMapping("/{id}/addresses")
    @RequirePermission(menu = "MM_CORP", authorities = {"AC_SYS", "AC_SAL", "AC_PRO", "AC_WHS"}, accessType = AccessType.READ)
    public List<ClientAddressResponseDTO> getClientAddresses(
            @Parameter(description = "고객사 ID", example = "1", required = true)
            @PathVariable int id) {
        return clientInfoService.getClientAddresses(id);
    }
}
