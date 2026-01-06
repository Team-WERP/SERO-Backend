package com.werp.sero.production.command.application.service;

import com.werp.sero.common.util.DateTimeUtils;
import com.werp.sero.employee.command.domain.aggregate.Employee;
import com.werp.sero.material.command.domain.aggregate.Bom;
import com.werp.sero.material.command.domain.aggregate.Material;
import com.werp.sero.material.command.domain.repository.BomRepository;
import com.werp.sero.material.command.domain.repository.MaterialRepository;
import com.werp.sero.material.exception.MaterialNotFoundException;
import com.werp.sero.notification.command.domain.aggregate.enums.NotificationType;
import com.werp.sero.notification.command.infrastructure.event.NotificationEvent;
import com.werp.sero.order.command.application.service.SOStateService;
import com.werp.sero.order.command.domain.aggregate.SalesOrderItemHistory;
import com.werp.sero.order.command.domain.repository.SalesOrderItemHistoryRepository;
import com.werp.sero.production.command.application.dto.*;
import com.werp.sero.production.command.domain.aggregate.*;
import com.werp.sero.production.command.domain.aggregate.enums.Action;
import com.werp.sero.production.command.domain.repository.*;
import com.werp.sero.production.exception.*;
import com.werp.sero.system.command.application.service.DocumentSequenceCommandService;
import com.werp.sero.warehouse.command.domain.aggregate.WarehouseStock;
import com.werp.sero.warehouse.command.domain.repository.WarehouseStockRepository;
import com.werp.sero.warehouse.exception.WarehouseStockNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
    private final WarehouseStockRepository warehouseStockRepository;
    private final SalesOrderItemHistoryRepository soItemHistoryRepository;
    private final PRCommandService prCommandService;
    private final ApplicationEventPublisher eventPublisher;
    private final PRRepository prRepository;
    private final SOStateService soStateService;
    private final ProductionLineRepository productionLineRepository;
    private final PRItemRepository prItemRepository;
    private final MaterialRepository materialRepository;
    private final BomRepository bomRepository;
    private final WorkOrderMaterialRepository workOrderMaterialRepository;

    @Override
    @Transactional
    public void createWorkOrder(
            WorkOrderCreateRequestDTO request,
            Employee currentEmployee
    ) {
        if (request.getItems() == null || request.getItems().isEmpty()) {
            throw new InvalidWorkOrderRequestException();
        }

        // 라인 확정
        ProductionLine line = productionLineRepository.findById(request.getLineId())
                .orElseThrow(ProductionLineNotFoundException::new);

        LocalDate workDate = DateTimeUtils.parse(request.getWorkDate());

        // WorkOrder 신규 생성 (라인 + 날짜 기준)
        WorkOrder wo = woRepository.save(
                new WorkOrder(
                        documentSequenceCommandService.generateDocumentCode("DOC_WO"),
                        request.getWorkDate(),
                        line,
                        currentEmployee
                )
        );

        // 아이템 처리
        for (WorkOrderCreateRequestDTO.Item itemReq : request.getItems()) {

            if (itemReq.getQuantity() <= 0) {
                throw new WorkOrderInvalidQuantityException();
            }

            Integer ppId = itemReq.getPpId();
            Integer prItemId = itemReq.getPrItemId();

            // (ppId, prItemId) XOR 검증
            if ((ppId == null && prItemId == null) || (ppId != null && prItemId != null)) {
                throw new InvalidWorkOrderRequestException();
            }

            ProductionPlan pp = null;
            ProductionRequestItem prItem;

            if(ppId != null) {
                // --- PP(생산계힉) 기반 ---
                pp = ppRepository.findById(ppId)
                        .orElseThrow(ProductionPlanNotFoundException::new);

                if (!"PP_CONFIRMED".equals(pp.getStatus())) throw new InvalidProductionPlanStatusException();
                if (pp.getProductionLine().getId() != line.getId()) throw new ProductionLineMismatchException();

                LocalDate start = DateTimeUtils.parse(pp.getStartDate());
                LocalDate end = DateTimeUtils.parse(pp.getEndDate());
                if (workDate.isBefore(start) || workDate.isAfter(end)) throw new WorkOrderInvalidPeriodException();

                // 같은 WO 내 동일 PP 중복 방지(정책)
                if (woItemRepository.existsByWorkOrder_IdAndProductionPlan_Id(wo.getId(), pp.getId())) {
                    throw new WorkOrderItemAlreadyExistsException();
                }

                prItem = pp.getProductionRequestItem();

            } else {
                // --- PR 기반(계획 외/긴급) ---
                prItem = prItemRepository.findById(prItemId)
                        .orElseThrow(ProductionRequestItemNotFoundException::new);

                if (woItemRepository.existsByWorkOrder_IdAndProductionRequestItem_IdAndProductionPlanIsNull(wo.getId(), prItem.getId())) {
                    throw new WorkOrderItemAlreadyExistsException();
                }
            }

            WorkOrderItem woItem = new WorkOrderItem(wo, pp, prItem, itemReq.getQuantity());
            woItemRepository.save(woItem);

            Material fgMaterial;

            if (pp != null) {
                fgMaterial = pp.getMaterial();
            } else {
                String materialCode =
                        prItem.getSalesOrderItem().getItemCode();

                fgMaterial = materialRepository
                        .findByMaterialCode(materialCode)
                        .orElseThrow(MaterialNotFoundException::new);
            }

            List<Bom> bomList =
                    bomRepository.findByMaterial_Id(fgMaterial.getId());
            String now = DateTimeUtils.nowDateTime();

            for (Bom bom : bomList) {
                int addQty = woItem.getPlannedQuantity() * bom.getRequirement();
                if (addQty <= 0) continue;

                int rmId = bom.getRawMaterial().getId();

                WorkOrderMaterial wom = workOrderMaterialRepository
                        .findByWorkOrder_IdAndRawMaterial_Id(wo.getId(), rmId)
                        .orElseGet(() -> WorkOrderMaterial.create(wo, bom.getRawMaterial(), 0, now));

                wom.addPlannedQuantity(addQty); // planned_qty 누적
                wom.touch(now);                 // updated_at 갱신 같은 것

                workOrderMaterialRepository.save(wom);
            }



            if (!"PIS_PRODUCING".equals(prItem.getStatus())) prItem.changeStatus("PIS_PRODUCING");
            ProductionRequest pr = prItem.getProductionRequest();
            if (!"PR_PRODUCING".equals(pr.getStatus())) pr.changeStatus("PR_PRODUCING");
        }

        int totalQuantity =
                woItemRepository.sumPlannedQuantityByWorkOrderId(wo.getId());
        wo.recalculateQuantity(totalQuantity);
    }

    @Override
    @Transactional
    public void start(int woId, String note, Employee worker) {
        WorkOrder wo = woRepository.findByIdForUpdate(woId)
                .orElseThrow(WorkOrderNotFoundException::new);

        wo.start(worker);
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
    public void end(
            int woId,
            WorkOrderEndRequest request,
            Employee currentEmployee
    ) {

        WorkOrder wo = woRepository.findByIdForUpdate(woId)
                .orElseThrow(WorkOrderNotFoundException::new);

        // 중복 실적 방지
        if (workOrderResultRepository.existsByWorkOrderId(woId)) {
            throw new WorkOrderResultAlreadyExistsException();
        }

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

        // WO 종료
        wo.end(); // WO_RUN → WO_DONE

        // 아이템별 실적 반영 (PR_ITEM 누적 + 창고 재고 증가 + SO 이력 추가)
        for (WorkOrderEndRequest.ItemResult r : request.getItems()) {

            WorkOrderItem woi = woItemRepository
                    .findById(r.getWorkOrderItemId())
                    .orElseThrow(WorkOrderItemNotFoundException::new);

            int qty = r.getProducedQuantity();
            if (qty < 0) {
                throw new InvalidProducedQuantityException();
            }

            int remain = woi.getPlannedQuantity() - woi.getProducedQuantity();
            if (qty > remain) {
                throw new ExceedPlannedQuantityException();
            }

            // 1) WorkOrderItem 누적 반영
            woi.addProducedQuantity(qty);
            woi.complete(); // WOI_READY → WOI_DONE

            // 2) ProductionRequestItem 누적 반영
            ProductionRequestItem prItem = woi.getProductionRequestItem();
            prItem.addProducedQuantity(qty);

            if (prItem.getProducedQuantity() >= prItem.getQuantity()) {
                prItem.changeStatus("PIS_DONE");
            } else {
                prItem.changeStatus("PIS_PRODUCING");
            }

            // 3) WarehouseStock 증가 (material 기준)
            Material material;

            if (woi.getProductionPlan() != null) {
                material = woi.getProductionPlan().getMaterial();
            } else {
                String itemCode = woi.getProductionRequestItem().getSalesOrderItem().getItemCode();
                material = materialRepository.findByMaterialCode(itemCode)
                        .orElseThrow(MaterialNotFoundException::new);
            }

            WarehouseStock stock =
                    warehouseStockRepository.findByWarehouseIdAndMaterialId(1, material.getId())
                            .orElseThrow(WarehouseStockNotFoundException::new);
            stock.increaseStock(qty);

            // 4) SalesOrderItemHistory 추가
            int soItemId = prItem.getSalesOrderItem().getId();

            SalesOrderItemHistory history =
                    SalesOrderItemHistory.createForProductionIn(
                            soItemId,
                            qty,  // 이번 생산입고 수량만 저장 (증가분)
                            currentEmployee.getId(),
                            null  // 더 이상 previousHistory 필요 없음 (각 이벤트는 독립적으로 저장)
                    );

            soItemHistoryRepository.save(history);
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

        Set<Integer> prIds = request.getItems().stream()
                .map(r -> {
                    WorkOrderItem woi = woItemRepository
                            .findById(r.getWorkOrderItemId())
                            .orElseThrow();
                    return woi.getProductionRequestItem()
                            .getProductionRequest()
                            .getId();
                })
                .collect(Collectors.toSet());

        for (int prId : prIds) {
            ProductionRequest pr = prRepository.findById(prId)
                    .orElseThrow(ProductionRequestNotFoundException::new);

            String beforeStatus = pr.getStatus();
            prCommandService.updatePRStatusIfNeeded(prId);
            soStateService.updateOrderStateByHistory(pr.getSalesOrder().getId());

            // PR 완료 시 알림
            if (!"PR_DONE".equals(beforeStatus) && "PR_DONE".equals(pr.getStatus())) {

                Employee salesEmployee = pr.getDrafter(); // 생산요청 올린 영업 담당자

                eventPublisher.publishEvent(
                        new NotificationEvent(
                                NotificationType.PRODUCTION,
                                "생산 완료",
                                "생산요청 " + pr.getPrCode() + "의 생산이 완료되었습니다.",
                                salesEmployee.getId(),
                                "/production/requests/" + pr.getId()
                        )
                );
            }
        }
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
