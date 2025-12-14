package com.werp.sero.client.service;

import com.werp.sero.client.dto.ClientAddressResponseDTO;
import com.werp.sero.client.dto.ClientInfoResponseDTO;

import java.util.List;

public interface ClientService {

    /**
     * 고객사 기본 정보 조회
     *
     * @param clientId 고객사 ID
     * @return 고객사 기본 정보
     */
    ClientInfoResponseDTO getClientInfo(int clientId);

    /**
     * 고객사 배송지 목록 조회
     *
     * @param clientId 고객사 ID
     * @return 배송지 목록 (기본 배송지 우선)
     */
    List<ClientAddressResponseDTO> getClientAddresses(int clientId);
}
