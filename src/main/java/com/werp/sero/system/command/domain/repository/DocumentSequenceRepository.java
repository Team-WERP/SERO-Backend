package com.werp.sero.system.command.domain.repository;

import com.werp.sero.system.command.domain.aggregate.DocumentSequence;
import jakarta.persistence.LockModeType;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface DocumentSequenceRepository extends JpaRepository<DocumentSequence, Integer> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
        SELECT A
          FROM DocumentSequence A
         WHERE A.prefix = :prefix
           AND A.baseDate = :baseDate
    """)
    Optional<DocumentSequence> findForUpdate(
            @Param("prefix") String prefix,
            @Param("baseDate") String baseDate
    );
    
}
