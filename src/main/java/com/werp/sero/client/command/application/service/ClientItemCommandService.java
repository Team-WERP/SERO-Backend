package com.werp.sero.client.command.application.service;

import com.werp.sero.client.command.application.dto.ClientItemCreateRequest;
import com.werp.sero.client.command.application.dto.ClientItemCreateResponse;
import com.werp.sero.client.command.application.dto.ClientItemUpdateRequest;
import com.werp.sero.client.command.application.dto.ClientItemUpdateResponse;

public interface ClientItemCommandService {

    /* 설명. 고객사 거래 품목 등록 */
    ClientItemCreateResponse createClientItem(Integer clientId, ClientItemCreateRequest request, Integer currentUserId);

    /* 설명. 고객사 거래 품목 단가 수정 */
    ClientItemUpdateResponse updateClientItem(Integer clientId, Integer itemId, ClientItemUpdateRequest request, Integer currentUserId);

    /* 설명. 고객사 거래 품목 삭제 */
    void deleteClientItem(Integer clientId, Integer itemId);
}
