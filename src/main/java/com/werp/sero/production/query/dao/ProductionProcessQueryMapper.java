package com.werp.sero.production.query.dao;

import com.werp.sero.production.query.dto.LineMaterialListItemDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductionProcessQueryMapper {
    List<LineMaterialListItemDTO> selectLineMaterialList();

}
