package com.werp.sero.production.command.application.service;

import com.werp.sero.employee.command.domain.aggregate.Employee;
import com.werp.sero.material.exception.MaterialNotFoundException;
import com.werp.sero.production.command.application.dto.PPCreateRequestDTO;
import com.werp.sero.production.command.application.dto.PPCreateResponseDTO;
import com.werp.sero.production.command.application.dto.PPValidateRequestDTO;
import com.werp.sero.production.command.application.dto.PPValidationResponseDTO;
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

    @Override
    @Transactional
    public PPValidationResponseDTO validate(PPValidateRequestDTO request) {

        // PR Item 존재 여부
        String materialCode =
                ppValidateMapper.selectMaterialCodeByPRItem(request.getPrItemId());
        log.info("materialCode={}", materialCode);
        if (materialCode == null) {
            throw new ProductionRequestItemNotFoundException();
        }

        // 이미 계획 존재 여부 (PR Item당 1회 정책)
        int planCount =
                ppValidateMapper.countPlansByPRItem(request.getPrItemId());
        if (planCount > 0) {
            throw new ProductionPlanAlreadyExistsException();
        }

        // Material 조회
        Integer materialId =
                ppValidateMapper.selectMaterialId(materialCode);
        log.info("materialId={}", materialId);
        if (materialId == null) {
            throw new MaterialNotFoundException();
        }

        // line_material에서 cycle_time 조회
        Integer cycleTime =
                ppValidateMapper.selectCycleTime(
                        materialId,
                        request.getProductionLineId()
                );
        log.info("cycleTime={}", cycleTime);
        if (cycleTime == null || cycleTime <= 0) {
            throw new ProductionPlanLineNotCapableException();
        }

        // 기간 유효성
        LocalDate start = LocalDate.parse(request.getStartDate());
        LocalDate end = LocalDate.parse(request.getEndDate());
        if (start.isAfter(end)) {
            throw new ProductionPlanInvalidPeriodException();
        }
        long days = ChronoUnit.DAYS.between(start, end) + 1;

        // 라인 가용 시간 → capa 계산
        long totalAvailableSeconds = days * DAILY_AVAILABLE_SECONDS;
        long capa = totalAvailableSeconds / cycleTime;

        // 수량 검증
        if (request.getProductionQuantity() <= 0) {
            throw new ProductionPlanInvalidQuantityException();
        }
        if (request.getProductionQuantity() > capa) {
            throw new ProductionPlanCapacityExceededException();
        }

        return PPValidationResponseDTO.ok();
    }

    @Override
    @Transactional
    public PPCreateResponseDTO create(
            PPCreateRequestDTO request,
            Employee employee
    ) {

        PPValidationResponseDTO validation =
                validate(
                        new PPValidateRequestDTO(
                                request.getPrItemId(),
                                request.getProductionLineId(),
                                request.getStartDate(),
                                request.getEndDate(),
                                request.getProductionQuantity()
                        )
                );

        if (!validation.isValid()) {
            throw new IllegalStateException(validation.getMessage());
        }

        ProductionRequestItem prItem =
                prItemRepository.findById(request.getPrItemId())
                        .orElseThrow(ProductionRequestItemNotFoundException::new);

        ProductionLine productionLine =
                productionLineRepository.findById(request.getProductionLineId())
                        .orElseThrow(ProductionLineNotFoundException::new);

        String ppCode =
                documentSequenceCommandService.generateDocumentCode("DOC_PP");

        ProductionPlan plan = ProductionPlan.create(
                prItem,
                productionLine,
                employee,
                request.getStartDate(),
                request.getEndDate(),
                request.getProductionQuantity(),
                ppCode
        );
        ppRepository.save(plan);

        return new PPCreateResponseDTO(
                plan.getId(),
                plan.getPpCode()
        );
    }
}
