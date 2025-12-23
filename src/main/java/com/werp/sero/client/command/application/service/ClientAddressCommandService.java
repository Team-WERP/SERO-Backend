package com.werp.sero.client.command.application.service;


import com.werp.sero.client.command.application.dto.ClientAddressCreateRequest;
import com.werp.sero.client.command.application.dto.ClientAddressCreateResponse;
import com.werp.sero.client.command.application.dto.ClientAddressUpdateRequest;
import com.werp.sero.client.command.application.dto.ClientAddressUpdateResponse;
import com.werp.sero.employee.command.domain.aggregate.ClientEmployee;

public interface ClientAddressCommandService {

    /**
     * 로그인한 ClientEmployee가 요청한 clientId에 접근 권한이 있는지 검증
     *
     * @param clientEmployee 현재 로그인한 ClientEmployee
     * @param requestedClientId 요청한 clientId
     */
    void validateClientAccess(ClientEmployee clientEmployee, int requestedClientId);

    ClientAddressCreateResponse createAddress(int clientId,ClientAddressCreateRequest request);

    ClientAddressUpdateResponse updateAddress(int clientId, int addressId, ClientAddressUpdateRequest request);

    void deleteAddress(int clientId, int addressId);
}
