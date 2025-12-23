package com.werp.sero.production.query.dao;

import com.werp.sero.production.query.dto.*;
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

    List<PRDetailResponseDTO> selectPRDetail(int prId);

    PRBasicInfoDTO selectPRBasicInfo(
            @Param("prId") int prId
    );

    List<PRPlanItemResponseDTO> selectPRPlanItems(
            @Param("prId") int prId
    );
}
