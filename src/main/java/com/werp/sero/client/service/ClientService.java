package com.werp.sero.client.service;

import com.werp.sero.client.dto.ClientInfoResponseDTO;

public interface ClientService {

    /**
     * 고객사 기본 정보 조회
     *
     * @param clientId 고객사 ID
     * @return 고객사 기본 정보
     */
    ClientInfoResponseDTO getClientInfo(int clientId);
}
