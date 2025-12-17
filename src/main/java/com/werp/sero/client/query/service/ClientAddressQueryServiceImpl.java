package com.werp.sero.client.query.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.werp.sero.client.query.dao.ClientAddressMapper;
import com.werp.sero.client.query.dto.ClientAddressResponseDTO;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class ClientAddressQueryServiceImpl implements ClientAddressQueryService {


    private final ClientAddressMapper clientAddressMapper;

    @Override
    public List<ClientAddressResponseDTO> getClientAddresses(int clientId) {

        return clientAddressMapper.findByClientId(clientId);

    }


}
