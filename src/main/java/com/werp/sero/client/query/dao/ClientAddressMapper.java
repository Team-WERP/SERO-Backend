package com.werp.sero.client.query.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.werp.sero.client.query.dto.ClientAddressResponseDTO;

@Mapper
public interface ClientAddressMapper {

	List<ClientAddressResponseDTO> findByClientId(@Param("clientId") int clientId);
}
