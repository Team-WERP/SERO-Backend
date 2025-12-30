package com.werp.sero.production.command.application.service;

import com.werp.sero.employee.command.domain.aggregate.Employee;
import com.werp.sero.material.command.domain.aggregate.Material;
import com.werp.sero.material.command.domain.repository.MaterialRepository;
import com.werp.sero.material.exception.MaterialNotFoundException;
import com.werp.sero.production.command.application.dto.*;
import com.werp.sero.production.command.domain.aggregate.ProductionLine;
import com.werp.sero.production.command.domain.aggregate.ProductionPlan;
import com.werp.sero.production.command.domain.aggregate.ProductionRequestItem;
import com.werp.sero.production.command.domain.repository.PPRepository;
import com.werp.sero.production.command.domain.repository.PRItemRepository;
import com.werp.sero.production.command.domain.repository.ProductionLineRepository;
import com.werp.sero.production.exception.*;
import com.werp.sero.production.query.dao.PPValidateMapper;
import com.werp.sero.system.command.application.service.DocumentSequenceCommandService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
@Slf4j
@RequiredArgsConstructor
public class PPCommandServiceImpl implements PPCommandService{
    private static final int DAILY_AVAILABLE_SECONDS = 8 * 60 * 60; // 8시간
    private final PPValidateMapper ppValidateMapper;
    private final DocumentSequenceCommandService documentSequenceCommandService;
    private final PPRepository ppRepository;
    private final PRItemRepository prItemRepository;
    private final ProductionLineRepository productionLineRepository;
    private final PRCommandService prCommandService;
    private final MaterialRepository materialRepository;

    /**
     * 생산계획 검증
     */
    @Override
    @Transactional(readOnly = true)
    public PPValidationResponseDTO validate(PPValidateRequestDTO request) {

        // PR Item 존재 여부
        ProductionRequestItem prItem =
                prItemRepository.findById(request.getPrItemId())
                        .orElseThrow(ProductionRequestItemNotFoundException::new);

        // 계획 수립 대상만 검증 가능
        if (!"PIS_TARGET".equals(prItem.getStatus())) {
            throw new InvalidPRItemStatusException(
                    "생산계획 수립 대상(PIS_TARGET)만 확정할 수 있습니다."
            );
        }

        // 이미 확정된 계획 존재 여부
        int planCount =
                ppValidateMapper.countPlansByPRItem(request.getPrItemId());
        if (planCount > 0) {
            throw new ProductionPlanAlreadyExistsException();
        }

        // material 조회
        String materialCode =
                ppValidateMapper.selectMaterialCodeByPRItem(request.getPrItemId());
        if (materialCode == null) {
            throw new MaterialNotFoundException();
        }

        Integer materialId =
                ppValidateMapper.selectMaterialId(materialCode);
        if (materialId == null) {
            throw new MaterialNotFoundException();
        }

        // 기간 유효성
        LocalDate start = LocalDate.parse(request.getStartDate());
        LocalDate end = LocalDate.parse(request.getEndDate());
        if (start.isAfter(end)) {
            throw new ProductionPlanInvalidPeriodException();
        }

        long days = ChronoUnit.DAYS.between(start, end) + 1;
        int dailyCapa = productionLineRepository
                        .findDailyCapacityById(request.getProductionLineId());

        // 신규 계획 일일 생산량
        int newDailyQty = (int) Math.ceil(
                (double) request.getProductionQuantity() / days
        );

        // 수량 검증
        if (request.getProductionQuantity() <= 0) {
            throw new ProductionPlanInvalidQuantityException();
        }

        // 날짜별 검증
        for (LocalDate d = start; !d.isAfter(end); d = d.plusDays(1)) {
            int existingDailyQty =
                    ppValidateMapper.sumDailyPlannedQty(
                            request.getProductionLineId(),
                            d.toString()
                    );
            if (existingDailyQty + newDailyQty > dailyCapa) {
                return PPValidationResponseDTO.capacityExceeded(
                        d.toString(),
                        dailyCapa,
                        existingDailyQty + newDailyQty
                );
            }
        }

        return PPValidationResponseDTO.ok();
    }

    /**
     * 생산계획 수립 대상 추가
     */
    @Override
    @Transactional
    public void addToPlanningTarget(PPAddTargetRequestDTO request, Employee employee) {
        ProductionRequestItem prItem = prItemRepository.findById(request.getPrItemId())
                .orElseThrow(ProductionRequestItemNotFoundException::new);

        if(!"PIS_WAIT".equals(prItem.getStatus())) {
            throw new InvalidPRItemStatusException();
        }
        if (ppRepository.existsByProductionRequestItemIdAndStatus(prItem.getId(), "PP_DRAFT")
                || ppRepository.existsByProductionRequestItemIdAndStatus(prItem.getId(), "PP_CONFIRMED")) {
            throw new ProductionPlanAlreadyExistsException();
        }

        prItem.changeStatus("PIS_TARGET");

        String materialCode =
                ppValidateMapper.selectMaterialCodeByPRItem(request.getPrItemId());
        if (materialCode == null) {
            throw new MaterialNotFoundException();
        }

        Material material = materialRepository
                .findByMaterialCode(materialCode)
                .orElseThrow(MaterialNotFoundException::new);

        ProductionPlan draft = ProductionPlan.createDraft(prItem, material, employee);
        ppRepository.save(draft);
    }

    /**
     * 생산계획 확정 (DRAFT → CONFIRMED)
     */
    @Override
    @Transactional
    public PPCreateResponseDTO create(PPCreateRequestDTO request, Employee employee) {

        validate(new PPValidateRequestDTO(
                request.getPrItemId(),
                request.getProductionLineId(),
                request.getStartDate(),
                request.getEndDate(),
                request.getProductionQuantity()
        ));

        ProductionRequestItem prItem =
                prItemRepository.findById(request.getPrItemId())
                        .orElseThrow(ProductionRequestItemNotFoundException::new);

        ProductionLine productionLine =
                productionLineRepository.findById(request.getProductionLineId())
                        .orElseThrow(ProductionLineNotFoundException::new);

        ProductionPlan plan =
                ppRepository.findByProductionRequestItemIdAndStatus(
                                prItem.getId(),
                                "PP_DRAFT"
                        )
                        .orElseThrow(ProductionPlanDraftNotFoundException::new);

        String ppCode =
                documentSequenceCommandService.generateDocumentCode("DOC_PP");

        plan.confirm(
                productionLine,
                employee,
                request.getStartDate(),
                request.getEndDate(),
                request.getProductionQuantity(),
                ppCode
        );
        prItem.changeStatus("PIS_PLANNED");
        prCommandService.updatePRStatusIfNeeded(prItem.getProductionRequest().getId());

        return new PPCreateResponseDTO(
                plan.getId(),
                plan.getPpCode()
        );
    }
}
