package com.werp.sero.shipping.query.service;

import com.werp.sero.shipping.query.dto.GIFilterDTO;
import com.werp.sero.shipping.query.dto.GIListResponseDTO;

import java.util.List;

/**
 * 출고지시 조회 서비스 인터페이스
 */
public interface GIListQueryService {


    List<GIListResponseDTO> findGoodsIssueList(GIFilterDTO filter, Integer page);


}
