package com.werp.sero.production.command.application.service;

import com.werp.sero.common.util.DateTimeUtils;
import com.werp.sero.employee.command.domain.aggregate.Employee;
import com.werp.sero.production.command.application.dto.WorkOrderCreateRequestDTO;
import com.werp.sero.production.command.application.dto.WorkOrderEndRequest;
import com.werp.sero.production.command.domain.aggregate.ProductionPlan;
import com.werp.sero.production.command.domain.aggregate.WorkOrder;
import com.werp.sero.production.command.domain.aggregate.WorkOrderHistory;
import com.werp.sero.production.command.domain.aggregate.WorkOrderResult;
import com.werp.sero.production.command.domain.aggregate.enums.Action;
import com.werp.sero.production.command.domain.repository.PPRepository;
import com.werp.sero.production.command.domain.repository.WORepository;
import com.werp.sero.production.command.domain.repository.WorkOrderHistoryRepository;
import com.werp.sero.production.command.domain.repository.WorkOrderResultRepository;
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

    @Override
    @Transactional
    public void createWorkOrder(
            WorkOrderCreateRequestDTO request,
            Employee currentEmployee
    ) {
        // 1. 생산계획 조회
        ProductionPlan pp = ppRepository.findById(request.getPpId())
                .orElseThrow(ProductionPlanNotFoundException::new);

        if (!"PP_CONFIRMED".equals(pp.getStatus())) {
            throw new InvalidProductionPlanStatusException();
        }

        // 2. 동일 날짜 작업지시 중복 방지
        boolean exists =
                woRepository.existsByProductionPlan_IdAndWorkDate(
                        pp.getId(),
                        request.getWorkDate()
                );

        if (exists) {
            throw new WorkOrderAlreadyExistsException();
        }

        // 3. 작업일자 검증 (PP 기간 내)
        LocalDate start = DateTimeUtils.parse(pp.getStartDate());
        LocalDate end   = DateTimeUtils.parse(pp.getEndDate());
        LocalDate work  = DateTimeUtils.parse(request.getWorkDate());

        if (work.isBefore(start) || work.isAfter(end)) {
            throw new WorkOrderInvalidPeriodException();
        }

        // 4. 수량 검증
        int requestQty = request.getQuantity();
        if (requestQty <= 0) {
            throw new WorkOrderInvalidQuantityException();
        }

        // 5. 잔여 수량 계산
        int producedQty =
                workOrderResultRepository.sumGoodQuantityByPpId(pp.getId());

        int remainingQty =
                pp.getProductionQuantity() - producedQty;

        if (requestQty > remainingQty) {
            throw new WorkOrderInvalidQuantityException();
        }

        // 6. 작업지시 생성
        WorkOrder wo = new WorkOrder(
                documentSequenceCommandService.generateDocumentCode("DOC_WO"),
                request.getWorkDate(),
                requestQty,
                pp.getProductionRequestItem().getProductionRequest(),
                pp,
                currentEmployee,
                currentEmployee
        );

        woRepository.save(wo);
    }

    @Override
    @Transactional
    public void start(int woId, String note) {
        WorkOrder wo = woRepository.findByIdForUpdate(woId)
                .orElseThrow(() -> new IllegalArgumentException("작업지시가 존재하지 않습니다."));

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
                .orElseThrow(() -> new IllegalArgumentException("작업지시가 존재하지 않습니다."));

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
                .orElseThrow(() -> new IllegalArgumentException("작업지시가 존재하지 않습니다."));

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
                .orElseThrow(() -> new IllegalArgumentException("작업지시가 존재하지 않습니다."));

        if (workOrderResultRepository.existsByWorkOrderId(woId)) {
            throw new IllegalStateException("이미 작업 실적이 등록되었습니다.");
        }
        wo.end(); // WO_RUN → WO_DONE

        int workMinutes = DateTimeUtils.minutesBetween(
                request.getStartTime(),
                request.getEndTime()
        );
        if (workMinutes <= 0) {
            throw new IllegalArgumentException("작업 시간이 올바르지 않습니다.");
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
