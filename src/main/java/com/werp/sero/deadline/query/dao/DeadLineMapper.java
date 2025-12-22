package com.werp.sero.deadline.query.dao;


import com.werp.sero.deadline.query.dto.DeadLineQueryResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;


@Mapper
public interface DeadLineMapper {


    // 자재 코드로 LineMaterial 정보 조회
    Optional<LineMaterialInfo> findLineMaterialByMaterialCode(@Param("materialCode") String materialCode);

    // 특정 생산라인(품목)의 생산계획 종료일(납기일) 조회
    
    Optional<String> findLastestProduction(@Param("productionLineId") int  productionLineId);



    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    class LineMaterialInfo {
        private int lineMaterialId;
        private int productionLineId;
        private String productionLineName;
        private String productionLineStatus;
        private int cycleTime;
    }




    

}
