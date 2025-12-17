package com.werp.sero.client.command.domain.repository;


import com.werp.sero.client.command.domain.aggregate.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {
}
