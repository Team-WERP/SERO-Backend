package com.werp.sero.shipping.query.dao;

import com.werp.sero.shipping.query.dto.GIDetailResponseDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GIMapper {

    /**
     * 출고지시 ID로 출고지시서 상세 조회
     * @param id 출고지시 ID
     * @return 출고지시서 상세 정보
     */
    GIDetailResponseDTO findById(Long id);
}
