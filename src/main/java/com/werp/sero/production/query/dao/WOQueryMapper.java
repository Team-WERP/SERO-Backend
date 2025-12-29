package com.werp.sero.production.query.dao;

import com.werp.sero.production.query.dto.WOByDateResponseDTO;
import com.werp.sero.production.query.dto.WOByPRResponseDTO;
import com.werp.sero.production.query.dto.WOByPPResponseDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface WOQueryMapper {

    List<WOByDateResponseDTO> selectByDate(String date);
}
