package com.werp.sero.client.query.dao;

import com.werp.sero.client.query.dto.ClientDetailResponseDTO;
import com.werp.sero.client.query.dto.ClientListResponseDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ClientMapper {

    /**
     * 고객사 목록 조회
     * @return 고객사 목록
     */
    List<ClientListResponseDTO> findAllClients();

    /**
     * 고객사 상세 조회 (기본 정보)
     * @param clientId 고객사 ID
     * @return 고객사 상세 정보
     */
    ClientDetailResponseDTO findClientById(@Param("clientId") Integer clientId);
}
