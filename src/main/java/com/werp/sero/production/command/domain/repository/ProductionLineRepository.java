package com.werp.sero.production.command.domain.repository;

import com.werp.sero.production.command.domain.aggregate.ProductionLine;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductionLineRepository extends JpaRepository<ProductionLine, Integer> {
    @Query(""" 
    select A.dailyCapacity
    from ProductionLine A
    where A.id = :lineId
    """)
    int findDailyCapacityById(@Param("lineId") int lineId);
}
