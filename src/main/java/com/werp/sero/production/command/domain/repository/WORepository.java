package com.werp.sero.production.command.domain.repository;

import com.werp.sero.production.command.domain.aggregate.WorkOrder;
import jakarta.persistence.LockModeType;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface WORepository extends JpaRepository<WorkOrder, Integer> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select A from WorkOrder A where A.id = :id")
    Optional<WorkOrder> findByIdForUpdate(@Param("id") int id);

    Optional<WorkOrder> findByProductionLineIdAndWorkDate(
            int lineId,
            String workDate
    );

}
