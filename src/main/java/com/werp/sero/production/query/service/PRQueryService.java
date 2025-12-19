package com.werp.sero.production.query.service;

import com.werp.sero.production.query.dto.PRDraftDetailResponseDTO;
import com.werp.sero.production.query.dto.PRDraftListResponseDTO;

import java.util.List;

public interface PRQueryService {
    List<PRDraftListResponseDTO> getDraftsByDrafter(int drafterId, Integer soId);

    PRDraftDetailResponseDTO getDraftDetail(int prId, int drafterId);
}
