package com.werp.sero.client.repository;

import com.werp.sero.client.entity.ClientEmployee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientEmployeeRepository extends JpaRepository<ClientEmployee, Integer> {
    Optional<ClientEmployee> findByEmail(final String email);
}