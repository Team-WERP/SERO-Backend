package com.werp.sero.client.command.domain.repository;

import com.werp.sero.client.command.application.dto.ClientAddressCreateResponse;
import com.werp.sero.client.command.domain.aggregate.Client;
import com.werp.sero.client.command.domain.aggregate.ClientAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ClientAddressCommandRepository extends JpaRepository<ClientAddress, Integer> {

}