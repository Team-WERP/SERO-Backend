package com.werp.sero.client.command.application.service;


import com.werp.sero.client.command.application.dto.ClientAddressCreateRequest;
import com.werp.sero.client.command.application.dto.ClientAddressCreateResponse;
import com.werp.sero.client.command.application.dto.ClientAddressUpdateRequest;
import com.werp.sero.client.command.application.dto.ClientAddressUpdateResponse;

public interface ClientAddressCommandService {

    ClientAddressCreateResponse createAddress(int clientId,ClientAddressCreateRequest request);

    ClientAddressUpdateResponse updateAddress(int clientId, int addressId, ClientAddressUpdateRequest request);
}
