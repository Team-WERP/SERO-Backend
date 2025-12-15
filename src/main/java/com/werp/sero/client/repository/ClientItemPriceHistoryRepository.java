package com.werp.sero.client.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.werp.sero.client.entity.ClientItemPriceHistory;

@Repository
public interface ClientItemPriceHistoryRepository extends JpaRepository<ClientItemPriceHistory, Integer>{
    
    List<ClientItemPriceHistory> findByClientIdAndClientItemIdOrderByCreatedAtDesc(
        int clientId,
        int clientItemId
    );
    

}
