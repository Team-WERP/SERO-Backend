package com.werp.sero.production.query.service;

import com.werp.sero.common.error.ErrorCode;
import com.werp.sero.common.error.exception.BusinessException;
import com.werp.sero.order.command.domain.repository.SORepository;
import com.werp.sero.order.exception.SalesOrderNotFoundException;
import com.werp.sero.production.exception.ProductionRequestNotFoundException;
import com.werp.sero.production.query.dao.PRQueryMapper;
import com.werp.sero.production.query.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PRQueryServiceImpl implements PRQueryService{
    private final PRQueryMapper prQueryMapper;
    private final SORepository soRepository;

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

    @Override
    @Transactional(readOnly = true)
    public List<PRListResponseDTO> getPRList(PRListSearchCondition condition) {
        return prQueryMapper.selectPRList(condition);
    }

    @Override
    @Transactional(readOnly = true)
    public PRDetailResponseDTO getDetail(int prId) {
        List<PRDetailResponseDTO> list = prQueryMapper.selectPRDetail(prId);

        return list.stream()
                .findFirst()
                .orElseThrow(ProductionRequestNotFoundException::new);
    }

    @Override
    public PRPlanItemListResponseDTO getPlanItems(int prId) {

        PRBasicInfoDTO pr = Optional
                .ofNullable(prQueryMapper.selectPRBasicInfo(prId))
                .orElseThrow(ProductionRequestNotFoundException::new);

        List<PRPlanItemResponseDTO> items
                = prQueryMapper.selectPRPlanItems(prId);

        return new PRPlanItemListResponseDTO(
                pr.getPrId(),
                pr.getPrCode(),
                pr.getStatus(),
                items
        );
    }

    @Override
    public List<PRListResponseDTO> getListByOrderId(final int orderId) {
        soRepository.findById(orderId).orElseThrow(SalesOrderNotFoundException::new);

        List<PRListResponseDTO> prList = prQueryMapper.selectPRListByOrderId(orderId);

        return prList;
    }
}
