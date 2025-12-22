package com.werp.sero.production.command.application.service;

import com.werp.sero.common.util.DateTimeUtils;
import com.werp.sero.employee.command.domain.aggregate.Employee;
import com.werp.sero.production.command.domain.aggregate.ProductionPlan;
import com.werp.sero.production.command.domain.aggregate.ProductionRequest;
import com.werp.sero.production.command.domain.aggregate.WorkOrder;
import com.werp.sero.production.command.domain.repository.PPRepository;
import com.werp.sero.production.command.domain.repository.WORepository;
import com.werp.sero.production.exception.*;
import com.werp.sero.system.command.application.service.DocumentSequenceCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WOCommandServiceImpl implements WOCommandService{
    private final PPRepository ppRepository;
    private final WORepository woRepository;
    private final DocumentSequenceCommandService documentSequenceCommandService;

    @Override
    @Transactional
    public void createFromProductionPlan(int ppId, Employee currentEmployee) {

        // 데이터 검증
        ProductionPlan pp = ppRepository.findById(ppId)
                .orElseThrow(ProductionPlanNotFoundException::new);

        if(!"PP_CONFIRMED".equals(pp.getStatus())) {
            throw new InvalidProductionPlanStatusException();
        }

        if(woRepository.existsByProductionPlan_Id(ppId)) {
            throw new WorkOrderAlreadyExistsException();
        }

        // 기간 계산
        LocalDate start = DateTimeUtils.parse(pp.getStartDate());
        LocalDate end = DateTimeUtils.parse(pp.getEndDate());

        if(start.isAfter(end)) {
            throw new WorkOrderInvalidPeriodException();
        }

        int days = (int) (end.toEpochDay() - start.toEpochDay()) + 1;
        if(days <= 0) {
            throw new WorkOrderInvalidPeriodException();
        }

        // 수량 분배
        int totalQuantity = pp.getProductionQuantity();
        if(totalQuantity <= 0) {
            throw new ProductionPlanInvalidQuantityException();
        }

        int baseQuantity = totalQuantity / days;
        int remainder = totalQuantity % days;

        // 작업지시 생성
        ProductionRequest pr = pp.getProductionRequestItem().getProductionRequest();
        List<WorkOrder> workOrders = new ArrayList<>();

        for(int i=0; i<days; i++) {
            LocalDate workDate = start.plusDays(i);
            int quantity = baseQuantity + (i < remainder ? 1 : 0);

            WorkOrder wo = new WorkOrder(
                    documentSequenceCommandService.generateDocumentCode("DOC_WO"),
                    workDate.toString(),
                    quantity,
                    pr,
                    pp,
                    currentEmployee,
                    currentEmployee
            );

            workOrders.add(wo);
        }

        woRepository.saveAll(workOrders);
    }
}
