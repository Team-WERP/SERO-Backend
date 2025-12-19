package com.werp.sero.production.query.service;

import com.werp.sero.production.query.dto.PRDraftListResponseDTO;

import java.util.List;

public interface PRQueryService {
    List<PRDraftListResponseDTO> getDraftsByDrafter(int drafterId);
}
