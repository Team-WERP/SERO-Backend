package com.werp.sero.client.service;

import java.util.List;

import com.werp.sero.client.dto.ClientItemResponse;

public interface ClientItemService {

 List<ClientItemResponse> getClientItems(int clientId, String keyword, String status);

}
