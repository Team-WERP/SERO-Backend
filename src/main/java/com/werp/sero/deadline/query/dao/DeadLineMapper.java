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
