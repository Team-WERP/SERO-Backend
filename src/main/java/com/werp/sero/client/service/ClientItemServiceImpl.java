package com.werp.sero.client.service;

import com.werp.sero.client.dto.ClientItemPriceHistoryResponse;
import com.werp.sero.client.dto.ClientItemResponse;
import com.werp.sero.client.entity.ClientItem;
import com.werp.sero.client.entity.ClientItemPriceHistory;
import com.werp.sero.client.repository.ClientItemPriceHistoryRepository;
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
    private final ClientItemPriceHistoryRepository clientItemPriceHistoryRepository;

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


    @Override
    public List<ClientItemPriceHistoryResponse> getPriceHistory(int clientId, int itemId) {

        // 1, clientItem이 해당 client에 속하는지 검증
        clientItemRepository.findById(itemId)
            .filter(item -> item.getClient().getId() == clientId)
            .orElseThrow(() -> new RuntimeException("해당 품목을 찾을 수 없습니다."));

        // 2. 단가 이력 조회
        List<ClientItemPriceHistory> histories = 
            clientItemPriceHistoryRepository.findByClientIdAndClientItemIdOrderByCreatedAtDesc(clientId, itemId); 

        return histories.stream()
                .map(ClientItemPriceHistoryResponse::from)
                .collect(Collectors.toList());

    }
    
}
