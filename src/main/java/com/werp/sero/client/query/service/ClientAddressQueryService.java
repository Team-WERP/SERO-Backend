package com.werp.sero.client.query.service;

import java.util.List;

import com.werp.sero.client.query.dto.ClientAddressResponseDTO;

public interface ClientAddressQueryService {

    List<ClientAddressResponseDTO> getClientAddresses(int clientId);
    
}
