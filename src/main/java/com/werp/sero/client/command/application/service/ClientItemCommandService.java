package com.werp.sero.client.command.application.service;

import com.werp.sero.client.command.application.dto.ClientItemCreateRequest;
import com.werp.sero.client.command.application.dto.ClientItemCreateResponse;

public interface ClientItemCommandService {

    /**
     * 고객사 거래 품목 등록
     * @param clientId 고객사 ID
     * @param request 거래 품목 등록 요청 DTO
     * @return 등록된 거래 품목 정보
     */
    ClientItemCreateResponse createClientItem(Integer clientId, ClientItemCreateRequest request);
}
