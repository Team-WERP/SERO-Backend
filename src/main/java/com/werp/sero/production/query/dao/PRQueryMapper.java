package com.werp.sero.production.query.dao;

import com.werp.sero.production.query.dto.PRDraftDetailResponseDTO;
import com.werp.sero.production.query.dto.PRDraftListResponseDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PRQueryMapper {
    List<PRDraftListResponseDTO> findDraftsByDrafter(int drafterId, Integer soId);

    PRDraftDetailResponseDTO findDraftDetail(int prId, int drafterId);
}
