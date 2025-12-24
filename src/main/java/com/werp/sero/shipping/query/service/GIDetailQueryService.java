package com.werp.sero.shipping.query.service;

import com.werp.sero.shipping.query.dto.GIDetailResponseDTO;

public interface GIDetailQueryService {

    /**
     * 출고지시 번호로 출고지시서 상세 조회
     * @param giCode 출고지시 번호
     * @return 출고지시서 상세 정보
     */
    GIDetailResponseDTO getGoodsIssueDetail(String giCode);
}
