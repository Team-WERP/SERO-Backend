package com.werp.sero.client.repository;

import com.werp.sero.client.entity.ClientAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClientAddressRepository extends JpaRepository<ClientAddress, Integer> {

    /* 설명. 고객사별 배송지 목록 조회 */
    @Query("SELECT ca FROM ClientAddress ca WHERE ca.client.id = :clientId ORDER BY ca.isDefault DESC, ca.createdAt DESC")
    List<ClientAddress> findByClientIdOrderByDefault(int clientId);
}
