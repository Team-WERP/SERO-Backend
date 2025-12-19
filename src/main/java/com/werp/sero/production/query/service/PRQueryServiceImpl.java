package com.werp.sero.production.query.service;

import com.werp.sero.common.error.ErrorCode;
import com.werp.sero.common.error.exception.BusinessException;
import com.werp.sero.production.query.dao.PRQueryMapper;
import com.werp.sero.production.query.dto.PRDraftDetailResponseDTO;
import com.werp.sero.production.query.dto.PRDraftListResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PRQueryServiceImpl implements PRQueryService{
    private final PRQueryMapper prQueryMapper;

    @Override
    public List<PRDraftListResponseDTO> getDraftsByDrafter(int drafterId, Integer soId, String soCode) {
        return prQueryMapper.findDraftsByDrafter(drafterId, soId, soCode);
    }

    @Override
    public PRDraftDetailResponseDTO getDraftDetail(int prId, int drafterId) {
        return Optional.ofNullable(
                prQueryMapper.findDraftDetail(prId, drafterId)
        ).orElseThrow(() -> new BusinessException(ErrorCode.PR_DRAFT_NOT_FOUND));
    }
}
