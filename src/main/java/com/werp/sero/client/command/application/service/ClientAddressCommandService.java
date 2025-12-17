package com.werp.sero.client.command.application.service;


import com.werp.sero.client.command.application.dto.ClientAddressCreateRequest;
import com.werp.sero.client.command.application.dto.ClientAddressCreateResponse;
import jakarta.validation.Valid;

public interface ClientAddressCommandService {

    ClientAddressCreateResponse createAddress(int clientId,ClientAddressCreateRequest request);
}
