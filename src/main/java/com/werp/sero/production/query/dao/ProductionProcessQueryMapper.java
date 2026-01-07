package com.werp.sero.production.query.dao;

import com.werp.sero.production.query.dto.LineMaterialListItemDTO;
import com.werp.sero.production.query.dto.ProductionProcessResponseDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProductionProcessQueryMapper {
    List<LineMaterialListItemDTO> selectLineMaterialList();

    List<ProductionProcessResponseDTO> selectProcessByLineMaterial(
            @Param("lineMaterialId") int lineMaterialId
    );}
