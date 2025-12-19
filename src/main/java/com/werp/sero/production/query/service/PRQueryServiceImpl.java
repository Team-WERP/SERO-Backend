package com.werp.sero.production.query.service;

import com.werp.sero.production.query.dao.PRQueryMapper;
import com.werp.sero.production.query.dto.PRDraftListResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PRQueryServiceImpl implements PRQueryService{
    private final PRQueryMapper prQueryMapper;

    @Override
    public List<PRDraftListResponseDTO> getDraftsByDrafter(int drafterId) {
        return prQueryMapper.findDraftsByDrafter(drafterId);
    }
}
