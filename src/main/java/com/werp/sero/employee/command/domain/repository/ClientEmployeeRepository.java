package com.werp.sero.employee.command.domain.repository;

import com.werp.sero.employee.command.domain.aggregate.ClientEmployee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientEmployeeRepository extends JpaRepository<ClientEmployee, Integer> {
    Optional<ClientEmployee> findByEmail(final String email);
}