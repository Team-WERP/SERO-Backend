package com.werp.sero.client.command.domain.repository;

import com.werp.sero.client.command.domain.aggregate.ClientItemPriceHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientItemPriceHistoryRepository extends JpaRepository<ClientItemPriceHistory, Integer> {
}
