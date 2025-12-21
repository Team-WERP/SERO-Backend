package com.werp.sero.shipping.query.service;

import com.werp.sero.shipping.query.dto.DODetailResponseDTO;

public interface DODetailQueryService {

    /**
     * 납품서 코드로 납품서 상세 조회
     *
     * @param doCode 납품서 코드 (예: DO-20251219-01)
     * @return 납품서 상세 정보
     */
    DODetailResponseDTO getDeliveryOrderDetail(String doCode);
}
