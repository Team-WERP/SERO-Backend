package com.werp.sero.client.command.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.werp.sero.client.command.domain.aggregate.ClientAddress;



public interface ClientAddressCommandRepository extends JpaRepository<ClientAddress, Integer> {

    // 특정 고객사의 모든 기본 배송지 조회
    List<ClientAddress> findByClientIdAndIsDefaultTrue(int clientId);
}