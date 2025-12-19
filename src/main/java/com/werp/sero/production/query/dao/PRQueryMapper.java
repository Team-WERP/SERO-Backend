package com.werp.sero.production.query.dao;

import com.werp.sero.production.query.dto.PRDraftDetailResponseDTO;
import com.werp.sero.production.query.dto.PRDraftListResponseDTO;
import com.werp.sero.production.query.dto.PRListResponseDTO;
import com.werp.sero.production.query.dto.PRListSearchCondition;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PRQueryMapper {
    List<PRDraftListResponseDTO> findDraftsByDrafter(
            @Param("drafterId") int drafterId,
            @Param("soId") Integer soId,
            @Param("soCode") String soCode
    );

    PRDraftDetailResponseDTO findDraftDetail(
            @Param("prId") int prId,
            @Param("drafterId") int drafterId
    );

    List<PRListResponseDTO> selectPRList(PRListSearchCondition condition);
}
