package com.werp.sero.system.query.dao;

import com.werp.sero.system.query.dto.CommonCodeDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommonCodeMapper {
    CommonCodeDTO findByCode(
            @Param("typeCode") String typeCode,
            @Param("code")String code
    );

    String findName(
            @Param("typeCode") String typeCode,
            @Param("code") String code
    );

    List<CommonCodeDTO> findByType(
            @Param("typeCode") String typeCode
    );
}
