package com.werp.sero.client.query.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.werp.sero.client.query.dto.ClientItemPriceHistoryResponseDTO;

@Mapper
public interface ClientItemPriceHistoryMapper {

    List<ClientItemPriceHistoryResponseDTO> findByClientIdAndClientItemIdOrderByCreatedAtDesc(
        @Param("clientId") int clientId,
        @Param("clientItemId") int clientItemId
    );
}
