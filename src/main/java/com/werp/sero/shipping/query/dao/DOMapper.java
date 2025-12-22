package com.werp.sero.shipping.query.dao;

import com.werp.sero.shipping.query.dto.DODetailResponseDTO;
import com.werp.sero.shipping.query.dto.DOListResponseDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DOMapper {

    /**
     * 납품서 코드로 납품서 상세 조회
     *
     * @param doCode 납품서 코드 (예: DO-20251219-01)
     * @return 납품서 상세 정보 (고객사 정보, 품목 정보 포함)
     */
    DODetailResponseDTO findByDoCode(@Param("doCode") String doCode);

    /**
     * 상태별 납품서 목록 조회
     *
     * @param status 납품서 상태 (예: DO_BEFORE_GI)
     * @return 납품서 목록
     */
    List<DOListResponseDTO> findByStatus(@Param("status") String status);

    List<String> selectDeliveryUrlsBySoId(final int orderId);
}
