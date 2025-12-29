package com.werp.sero.production.command.application.service;

import com.werp.sero.common.util.DateTimeUtils;
import com.werp.sero.employee.command.domain.aggregate.Employee;
import com.werp.sero.production.command.application.dto.*;
import com.werp.sero.production.command.domain.aggregate.*;
import com.werp.sero.production.command.domain.aggregate.enums.Action;
import com.werp.sero.production.command.domain.repository.*;
import com.werp.sero.production.exception.*;
import com.werp.sero.system.command.application.service.DocumentSequenceCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WOCommandServiceImpl implements WOCommandService {
    private final PPRepository ppRepository;
    private final WORepository woRepository;
    private final DocumentSequenceCommandService documentSequenceCommandService;
    private final WorkOrderResultRepository workOrderResultRepository;
    private final WorkOrderHistoryRepository workOrderHistoryRepository;
    private final WOItemRepository woItemRepository;
    private final WorkOrderItemDistributor distributor;

    @Override
    @Transactional
    public void createWorkOrder(
            WorkOrderCreateRequestDTO request,
            Employee currentEmployee
    ) {
        if (request.getItems() == null || request.getItems().isEmpty()) {
            throw new InvalidWorkOrderRequestException();
        }

        // 기준 PP 확보
        ProductionPlan firstPP = ppRepository.findById(request.getItems().get(0).getPpId())
                .orElseThrow(ProductionPlanNotFoundException::new);

        if (!"PP_CONFIRMED".equals(firstPP.getStatus())) {
            throw new InvalidProductionPlanStatusException();
        }

        ProductionLine line = firstPP.getProductionLine();
        if (line == null || line.getId() != request.getLineId()) {
            throw new ProductionLineMismatchException();
        }

        LocalDate workDate = DateTimeUtils.parse(request.getWorkDate());

        // WorkOrder 생성 or 조회 (라인 + 날짜 기준)
        WorkOrder wo = woRepository
                .findByProductionLine_IdAndWorkDate(line.getId(), request.getWorkDate())
                .orElseGet(() -> woRepository.save(
                        new WorkOrder(
                                documentSequenceCommandService.generateDocumentCode("DOC_WO"),
                                request.getWorkDate(),
                                line,
                                currentEmployee,
                                currentEmployee
                        )
                ));

        // PP 단위 처리
        for (WorkOrderCreateRequestDTO.Item itemReq : request.getItems()) {

            ProductionPlan pp = ppRepository.findById(itemReq.getPpId())
                    .orElseThrow(ProductionPlanNotFoundException::new);

            // PP 검증
            if (!"PP_CONFIRMED".equals(pp.getStatus())) {
                throw new InvalidProductionPlanStatusException();
            }

            if (pp.getProductionLine().getId() != line.getId()) {
                throw new ProductionLineMismatchException();
            }

            LocalDate start = DateTimeUtils.parse(pp.getStartDate());
            LocalDate end = DateTimeUtils.parse(pp.getEndDate());
            if (workDate.isBefore(start) || workDate.isAfter(end)) {
                throw new WorkOrderInvalidPeriodException();
            }

            if (woItemRepository.existsByWorkOrder_IdAndProductionPlan_Id(wo.getId(), pp.getId())) {
                throw new WorkOrderItemAlreadyExistsException();
            }

            if (itemReq.getQuantity() <= 0) {
                throw new WorkOrderInvalidQuantityException();
            }

            // WorkOrderItem 생성
            WorkOrderItem woItem = new WorkOrderItem(
                    wo,
                    pp,
                    pp.getProductionRequestItem(),
                    itemReq.getQuantity()
            );
            woItemRepository.save(woItem);

            // PR_ITEM 상태 변경
            ProductionRequestItem prItem = pp.getProductionRequestItem();
            if (!"PIS_PRODUCING".equals(prItem.getStatus())) {
                prItem.changeStatus("PIS_PRODUCING");
            }

            // PR 상태 변경 (같은 PR은 여러 번 와도 idempotent)
            ProductionRequest pr = prItem.getProductionRequest();
            if (!"PR_PRODUCING".equals(pr.getProductionStatus())) {
                pr.changeStatus("PR_PRODUCING");
            }
        }

        int totalQuantity =
                woItemRepository.sumPlannedQuantityByWorkOrderId(wo.getId());
        wo.recalculateQuantity(totalQuantity);
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

        // 중복 실적 방지
        if (workOrderResultRepository.existsByWorkOrderId(woId)) {
            throw new WorkOrderResultAlreadyExistsException();
        }

        // WO 종료
        wo.end(); // WO_RUN → WO_DONE

        // 작업 시간 검증
        int workMinutes = DateTimeUtils.minutesBetween(
                request.getStartTime(),
                request.getEndTime()
        );
        if (workMinutes <= 0) {
            throw new WorkOrderInvalidWorkTimeException();
        }

        // 아이템별 실적 검증
        int sum = request.getItems().stream()
                .mapToInt(WorkOrderEndRequest.ItemResult::getProducedQuantity)
                .sum();

        if (sum != request.getGoodQuantity()) {
            throw new InvalidDistributedQuantityException();
        }

        // 아이템별 실적 반영
        for (WorkOrderEndRequest.ItemResult r : request.getItems()) {

            WorkOrderItem item = woItemRepository
                    .findById(r.getWorkOrderItemId())
                    .orElseThrow(WorkOrderItemNotFoundException::new);

            if (r.getProducedQuantity() < 0) {
                throw new InvalidProducedQuantityException();
            }

            if (r.getProducedQuantity() > item.getPlannedQuantity()) {
                throw new ExceedPlannedQuantityException();
            }

            item.addProducedQuantity(r.getProducedQuantity());
            item.complete(); // WOI_READY → WOI_DONE
        }

        // WorkOrderResult 저장
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

        // History 저장
        WorkOrderHistory history = new WorkOrderHistory(
                wo,
                Action.END,
                request.getNote()
        );
        workOrderHistoryRepository.save(history);
    }

    @Override
    public WorkOrderResultPreviewResponseDTO previewResult(
            int woId,
            WorkOrderResultPreviewRequestDTO request
    ) {
        List<WorkOrderItem> items =
                woItemRepository.findByWorkOrderId(woId);

        List<WorkOrderItemPreviewDTO> distributed =
                distributor.distribute(items, request.getGoodQuantity());

        return new WorkOrderResultPreviewResponseDTO(
                request.getGoodQuantity(),
                distributed
        );
    }

}
