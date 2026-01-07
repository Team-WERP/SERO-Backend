package com.werp.sero.code.command.domain.repository;

import com.werp.sero.code.command.domain.aggregate.CommonCodeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommonCodeTypeRepository extends JpaRepository<CommonCodeType, Integer> {

    Optional<CommonCodeType> findByCode(String code);

    boolean existsByCode(String code);
}
