package com.werp.sero.client.query.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.werp.sero.client.query.dto.ClientItemResponseDTO;

@Mapper
public interface ClientItemMapper {


    List<ClientItemResponseDTO> findByClientId(@Param("clientId") int clientId,
                                               @Param("status") String status,
                                               @Param("keyword") String keyword);
    
    // 품목이 해당 고객사에 속하는지 검증 (고객사가 주문할 수 있는 품목인지)
    boolean existsByIdAndClientId(@Param("clientId") int clientId,
                                  @Param("itemId") int itemId);
}
