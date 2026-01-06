package com.werp.sero.client.command.domain.repository;

import com.werp.sero.client.command.domain.aggregate.ClientItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientItemRepository extends JpaRepository<ClientItem, Integer> {
}
