package com.werp.sero.production.query.service;

import com.werp.sero.production.query.dao.PRQueryMapper;
import com.werp.sero.production.query.dto.PRDraftDetailResponseDTO;
import com.werp.sero.production.query.dto.PRDraftListResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PRQueryServiceImpl implements PRQueryService{
    private final PRQueryMapper prQueryMapper;

    @Override
    public List<PRDraftListResponseDTO> getDraftsByDrafter(int drafterId, Integer soId) {
        return prQueryMapper.findDraftsByDrafter(drafterId, soId);
    }

    @Override
    public PRDraftDetailResponseDTO getDraftDetail(int prId, int drafterId) {
        PRDraftDetailResponseDTO detail = prQueryMapper.findDraftDetail(prId, drafterId);
        if (detail == null) {
            throw new IllegalArgumentException("임시 저장된 생산요청을 찾을 수 없거나 접근 권한이 없습니다.");
        }
        return detail;
    }
}
