package com.werp.sero.code.command.domain.repository;

import com.werp.sero.code.command.domain.aggregate.CommonCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommonCodeRepository extends JpaRepository<CommonCode, Integer> {

    Optional<CommonCode> findByCode(String code);

    boolean existsByCode(String code);
}
