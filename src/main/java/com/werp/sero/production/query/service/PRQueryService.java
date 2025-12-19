package com.werp.sero.production.query.service;

import com.werp.sero.production.query.dto.PRDraftDetailResponseDTO;
import com.werp.sero.production.query.dto.PRDraftListResponseDTO;
import com.werp.sero.production.query.dto.PRListResponseDTO;
import com.werp.sero.production.query.dto.PRListSearchCondition;

import java.util.List;

public interface PRQueryService {
    List<PRDraftListResponseDTO> getDraftsByDrafter(int drafterId, Integer soId, String soCode);

    PRDraftDetailResponseDTO getDraftDetail(int prId, int drafterId);

    List<PRListResponseDTO> getPRList(PRListSearchCondition condition);
}
