package com.werp.sero.code.query.dao;

import com.werp.sero.code.query.dto.CommonCodeManageDTO;
import com.werp.sero.code.query.dto.CommonCodeDetailManageDTO;
import com.werp.sero.code.query.dto.CommonCodeTypeManageDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommonCodeManageMapper {
    CommonCodeManageDTO findByCode(
            @Param("typeCode") String typeCode,
            @Param("code")String code
    );

    String findName(
            @Param("typeCode") String typeCode,
            @Param("code") String code
    );

    List<CommonCodeManageDTO> findByType(
            @Param("typeCode") String typeCode
    );

    // 공통코드 관리 페이지용
    List<CommonCodeTypeManageDTO> findAllCodeTypes();

    List<CommonCodeDetailManageDTO> findDetailsByType(
            @Param("typeCode") String typeCode
    );
}
