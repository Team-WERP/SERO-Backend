package com.werp.sero.production.command.application.service;

import com.werp.sero.common.util.DateTimeUtils;
import com.werp.sero.employee.command.domain.aggregate.Employee;
import com.werp.sero.production.command.application.dto.WorkOrderCreateRequestDTO;
import com.werp.sero.production.command.application.dto.WorkOrderEndRequest;
import com.werp.sero.production.command.domain.aggregate.*;
import com.werp.sero.production.command.domain.aggregate.enums.Action;
import com.werp.sero.production.command.domain.repository.*;
import com.werp.sero.production.exception.*;
import com.werp.sero.system.command.application.service.DocumentSequenceCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class WOCommandServiceImpl implements WOCommandService {
    private final PPRepository ppRepository;
    private final WORepository woRepository;
    private final DocumentSequenceCommandService documentSequenceCommandService;
    private final WorkOrderResultRepository workOrderResultRepository;
    private final WorkOrderHistoryRepository workOrderHistoryRepository;
    private final WOItemRepository woItemRepository;

    @Override
    @Transactional
    public void createWorkOrder(
            WorkOrderCreateRequestDTO request,
            Employee currentEmployee
    ) {
        if (request.getItems() == null || request.getItems().isEmpty()) {
            throw new InvalidWorkOrderRequestException();
        }

        // 1. 첫 PP 기준으로 기준 정보 확보
        ProductionPlan firstPP = ppRepository.findById(request.getItems().get(0).getPpId())
                .orElseThrow(ProductionPlanNotFoundException::new);

        if (!"PP_CONFIRMED".equals(firstPP.getStatus())) {
            throw new InvalidProductionPlanStatusException();
        }

        // 2. 라인 검증
        if (firstPP.getProductionLine() == null ||
                firstPP.getProductionLine().getId() != request.getLineId()) {
            throw new ProductionLineMismatchException();
        }

        ProductionLine line = firstPP.getProductionLine();

        // 3. 작업일자 검증
        LocalDate workDate = DateTimeUtils.parse(request.getWorkDate());

        // 4. WorkOrder 조회 or 생성
        WorkOrder wo = woRepository
                .findByProductionLine_IdAndWorkDate(line.getId(), request.getWorkDate())
                .orElseGet(() -> woRepository.save(
                        new WorkOrder(
                                documentSequenceCommandService.generateDocumentCode("DOC_WO"),
                                request.getWorkDate(),
                                firstPP.getProductionRequestItem().getProductionRequest(),
                                line,
                                currentEmployee,
                                currentEmployee
                        )
                ));

        // 5. PP별 WorkOrderItem 생성
        for (WorkOrderCreateRequestDTO.Item itemReq : request.getItems()) {

            ProductionPlan pp = ppRepository.findById(itemReq.getPpId())
                    .orElseThrow(ProductionPlanNotFoundException::new);

            // PP 상태 검증
            if (!"PP_CONFIRMED".equals(pp.getStatus())) {
                throw new InvalidProductionPlanStatusException();
            }

            // 라인 일치 검증
            if (pp.getProductionLine().getId() != line.getId()) {
                throw new ProductionLineMismatchException();
            }

            // 기간 검증
            LocalDate start = DateTimeUtils.parse(pp.getStartDate());
            LocalDate end = DateTimeUtils.parse(pp.getEndDate());
            if (workDate.isBefore(start) || workDate.isAfter(end)) {
                throw new WorkOrderInvalidPeriodException();
            }

            // 중복 PP 방지
            if (woItemRepository.existsByWorkOrder_IdAndProductionPlan_Id(wo.getId(), pp.getId())) {
                throw new WorkOrderItemAlreadyExistsException();
            }

            // 수량 검증
            if (itemReq.getQuantity() <= 0) {
                throw new WorkOrderInvalidQuantityException();
            }

            WorkOrderItem woItem = new WorkOrderItem(
                    wo,
                    pp,
                    pp.getProductionRequestItem(),
                    itemReq.getQuantity()
            );

            woItemRepository.save(woItem);
        }
    }

    @Override
    @Transactional
    public void start(int woId, String note) {
        WorkOrder wo = woRepository.findByIdForUpdate(woId)
                .orElseThrow(WorkOrderNotFoundException::new);

        wo.start();
        WorkOrderHistory woHistory = new WorkOrderHistory(
                wo,
                Action.START,
                note
        );

        workOrderHistoryRepository.save(woHistory);
    }

    @Override
    @Transactional
    public void pause(int woId, String note) {
        WorkOrder wo = woRepository.findByIdForUpdate(woId)
                .orElseThrow(WorkOrderNotFoundException::new);

        wo.pause(); // WO_RUN → WO_PAUSE
        WorkOrderHistory woHistory = new WorkOrderHistory(
                wo,
                Action.PAUSE,
                note
        );

        workOrderHistoryRepository.save(woHistory);
    }

    @Override
    @Transactional
    public void resume(int woId, String note) {
        WorkOrder wo = woRepository.findByIdForUpdate(woId)
                .orElseThrow(WorkOrderNotFoundException::new);

        wo.resume(); // WO_PAUSE → WO_RUN
        WorkOrderHistory woHistory = new WorkOrderHistory(
                wo,
                Action.RESUME,
                note
        );

        workOrderHistoryRepository.save(woHistory);
    }

    @Override
    @Transactional
    public void end(int woId, WorkOrderEndRequest request) {

        WorkOrder wo = woRepository.findByIdForUpdate(woId)
                .orElseThrow(WorkOrderNotFoundException::new);

        if (workOrderResultRepository.existsByWorkOrderId(woId)) {
            throw new WorkOrderResultAlreadyExistsException();
        }
        wo.end(); // WO_RUN → WO_DONE

        int workMinutes = DateTimeUtils.minutesBetween(
                request.getStartTime(),
                request.getEndTime()
        );
        if (workMinutes <= 0) {
            throw new WorkOrderInvalidWorkTimeException();
        }

        WorkOrderResult result = new WorkOrderResult(
                request.getGoodQuantity(),
                request.getDefectiveQuantity(),
                request.getStartTime(),
                request.getEndTime(),
                workMinutes,
                request.getNote(),
                wo
        );
        workOrderResultRepository.save(result);

        WorkOrderHistory history = new WorkOrderHistory(
                wo,
                Action.END,
                request.getNote()
        );
        workOrderHistoryRepository.save(history);
    }

}
