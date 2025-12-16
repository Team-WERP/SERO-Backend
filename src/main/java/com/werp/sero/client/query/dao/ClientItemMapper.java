package com.werp.sero.client.query.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.werp.sero.client.query.dto.ClientItemResponseDTO;

@Mapper
public interface ClientItemMapper {

    List<ClientItemResponseDTO> findByClientId(@Param("clientId") int clientId);

    List<ClientItemResponseDTO> findByClientIdAndKeyword(@Param("clientId") int clientId,
                                                          @Param("keyword") String keyword);

    List<ClientItemResponseDTO> findByClientIdAndStatus(@Param("clientId") int clientId,
                                                         @Param("status") String status);

    List<ClientItemResponseDTO> findByClientIdAndStatusAndKeyword(@Param("clientId") int clientId,
                                                                   @Param("status") String status,
                                                                   @Param("keyword") String keyword);
}
