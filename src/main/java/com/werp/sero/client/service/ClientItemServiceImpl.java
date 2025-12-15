package com.werp.sero.client.service;

import com.werp.sero.client.dto.ClientItemResponse;
import com.werp.sero.client.entity.ClientItem;
import com.werp.sero.client.repository.ClientItemRepository;
import com.werp.sero.client.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClientItemServiceImpl implements ClientItemService{

    private final ClientRepository clientRepository;
    private final ClientItemRepository clientItemRepository;


    @Override
    public List<ClientItemResponse> getClientItems(int clientId, String keyword, String status) {
       
        List<ClientItem> items;
        if (keyword != null && status != null) {
            // 둘다 있음 keyword로 검색 + status 필터
            items = clientItemRepository.findByClientIdAndStatusAndKeyword(clientId,status,keyword);
        }
        else if (keyword != null) {
            // keyword만 O
            items = clientItemRepository.findByClientIdAndKeyword(clientId,keyword);
        }
        else if (status != null) {
            // status만 O 
            items = clientItemRepository.findByClientIdAndStatus(clientId,status);
        }
        else {
            // 둘 다 X
            items = clientItemRepository.findByClientId(clientId);
        }
        return items.stream()
                .map(ClientItemResponse::from)
                .collect(Collectors.toList());
    }
}
