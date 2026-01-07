package com.werp.sero.client.query.service;

import com.werp.sero.client.query.dto.ClientDetailResponseDTO;
import com.werp.sero.client.query.dto.ClientListResponseDTO;
import com.werp.sero.client.query.dto.ClientPortalDetailResponseDTO;

import java.util.List;

public interface ClientQueryService {

    /**
     * 고객사 목록 조회
     * @return 고객사 목록
     */
    List<ClientListResponseDTO> getClientList();

    /**
     * 고객사 상세 조회
     * @param clientId 고객사 ID
     * @return 고객사 상세 정보
     */
    ClientDetailResponseDTO getClientDetail(Integer clientId);

    ClientPortalDetailResponseDTO getClientDetailForClient(final int id);
}
