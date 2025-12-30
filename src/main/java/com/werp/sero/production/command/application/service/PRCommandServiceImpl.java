package com.werp.sero.production.command.application.service;

import com.werp.sero.employee.command.domain.aggregate.Employee;
import com.werp.sero.employee.command.domain.repository.EmployeeRepository;
import com.werp.sero.employee.exception.EmployeeNotFoundException;
import com.werp.sero.order.command.domain.aggregate.SalesOrder;
import com.werp.sero.order.command.domain.aggregate.SalesOrderItem;
import com.werp.sero.order.command.domain.aggregate.SalesOrderItemHistory;
import com.werp.sero.order.command.domain.repository.SORepository;
import com.werp.sero.order.command.domain.repository.SOItemRepository;
import com.werp.sero.order.command.domain.repository.SalesOrderItemHistoryRepository;
import com.werp.sero.order.exception.SalesOrderItemNotFoundException;
import com.werp.sero.order.exception.SalesOrderNotFoundException;
import com.werp.sero.production.command.application.dto.PRDraftCreateRequestDTO;
import com.werp.sero.production.command.application.dto.PRDraftUpdateRequestDTO;
import com.werp.sero.production.command.application.dto.PRItemCreateRequestDTO;
import com.werp.sero.production.command.application.dto.PRItemDraftUpdateDTO;
import com.werp.sero.production.command.domain.aggregate.ProductionRequest;
import com.werp.sero.production.command.domain.aggregate.ProductionRequestItem;
import com.werp.sero.production.command.domain.repository.PRItemRepository;
import com.werp.sero.production.command.domain.repository.PRRepository;
import com.werp.sero.production.exception.*;
import com.werp.sero.system.command.application.service.DocumentSequenceCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PRCommandServiceImpl implements PRCommandService {
    private final PRRepository prRepository;
    private final PRItemRepository prtItemRepository;
    private final SORepository soRepository;
    private final SOItemRepository soItemRepository;
    private final EmployeeRepository employeeRepository;
    private final DocumentSequenceCommandService documentSequenceCommandService;
    private final SalesOrderItemHistoryRepository soItemHistoryRepository;
    private final PRPdfService prPdfService;

    @Override
    @Transactional
    public int createDraft(PRDraftCreateRequestDTO dto, Employee drafter) {
        SalesOrder so = soRepository.findById(dto.getSoId())
                .orElseThrow(SalesOrderNotFoundException::new);
        
        ProductionRequest pr = ProductionRequest.createDraft(
                so,
                drafter,
                dto.getDueAt(),
                dto.getReason()
        );

        prRepository.save(pr);

        if(dto.getItems() != null) {
            for(PRItemCreateRequestDTO itemDto : dto.getItems()) {
                if (itemDto.getQuantity() < 0) {
                    throw new ProductionItemQuantityInvalidException();
                }

                SalesOrderItem soItem = soItemRepository.findById(itemDto.getSoItemId())
                        .orElseThrow(SalesOrderItemNotFoundException::new);

                if (soItem.getSalesOrder().getId() != so.getId()) {
                    throw new ProductionItemNotInSalesOrderException();
                }

                ProductionRequestItem prItem = pr.addItem(soItem, itemDto.getQuantity());
                prtItemRepository.save(prItem);
            }
        }

        return pr.getId();
    }

    @Override
    @Transactional
    public void updateDraft(int prId, PRDraftUpdateRequestDTO dto, Employee employee) {
        ProductionRequest pr = prRepository.findById(prId)
                .orElseThrow(ProductionDraftNotFoundException::new);

        // 상태 및 작성자 검증
        if (!"PR_TMP".equals(pr.getStatus())) {
            throw new ProductionNotDraftException();
        }
        if (pr.getDrafter().getId() != employee.getId()) {
            throw new ProductionDraftNotFoundException();
        }

        // 일자,사유 수정
        if (dto.getDueAt() != null) {
            pr.changeDueAt(dto.getDueAt());
        }
        if (dto.getReason() != null) {
            pr.changeReason(dto.getReason());
        }

        // 품목 수량 수정
        if (dto.getItems() != null) {
            int total = 0;
            for (PRItemDraftUpdateDTO itemDto : dto.getItems()) {

                if (itemDto.getQuantity() < 0) {
                    throw new ProductionItemQuantityInvalidException();
                }

                SalesOrderItem soItem = soItemRepository.findById(itemDto.getSoItemId())
                        .orElseThrow(SalesOrderItemNotFoundException::new);

                if (soItem.getSalesOrder().getId() != pr.getSalesOrder().getId()) {
                    throw new ProductionItemNotInSalesOrderException();
                }

                ProductionRequestItem prItem = prtItemRepository
                        .findByProductionRequestIdAndSalesOrderItemId(prId, soItem.getId())
                        .orElseGet(() -> pr.addItem(soItem, 0));

                prItem.changeQuantity(itemDto.getQuantity());
                prtItemRepository.save(prItem);
                
                total += itemDto.getQuantity();
            }
            pr.changeTotalQuantity(total);
        }
    }

    @Override
    @Transactional
    public void request(int prId, Employee employee) {
        ProductionRequest pr = prRepository.findById(prId)
                .orElseThrow(ProductionDraftNotFoundException::new);

        // 검증 (상태, 작성자, 수량)
        if (!"PR_TMP".equals(pr.getStatus())) {
            throw new ProductionNotDraftException();
        }
        if (pr.getDrafter().getId() != employee.getId()) {
            throw new ProductionDraftNotFoundException();
        }
        if (pr.getTotalQuantity() <= 0) {
            throw new ProductionRequestEmptyException();
        }

        String prCode = documentSequenceCommandService.generateDocumentCode("DOC_PR");
        pr.request(prCode);

        List<ProductionRequestItem> items =
                prtItemRepository.findAllByProductionRequest_Id(prId);

        for (ProductionRequestItem prItem : items) {

            int soItemId = prItem.getSalesOrderItem().getId();
            int qty = prItem.getQuantity();

            SalesOrderItemHistory prev =
                    soItemHistoryRepository
                            .findTopBySoItemIdOrderByIdDesc(soItemId)
                            .orElse(null);

            SalesOrderItemHistory history =
                    SalesOrderItemHistory.createForProductionRequest(
                            soItemId,
                            qty,
                            employee.getId(),
                            prev
                    );

            prItem.changeStatus("PIS_WAIT");
            soItemHistoryRepository.save(history);
        }
    }

    @Override
    @Transactional
    public void assignManager(int prId, int managerId) {
        ProductionRequest pr = prRepository.findById(prId)
                .orElseThrow(ProductionRequestNotFoundException::new);

        Employee manager = employeeRepository.findById(managerId)
                .orElseThrow(EmployeeNotFoundException::new);

        pr.assignManager(manager);
        
        String pdfUrl = prPdfService.generateAndUpload(prId);
        pr.updatePrUrl(pdfUrl);
    }

    @Transactional
    @Override
    public void updatePRStatusIfNeeded(int prId) {
        List<ProductionRequestItem> items =
                prtItemRepository.findAllByProductionRequest_Id(prId);

        boolean hasTarget = false;
        boolean hasPlanned = false;
        boolean hasProducing = false;
        boolean allDone = true;

        for (var item : items) {
            switch (item.getStatus()) {
                case "PIS_TARGET" -> hasTarget = true;
                case "PIS_PLANNED" -> hasPlanned = true;
                case "PIS_PRODUCING" -> hasProducing = true;
                default -> { /* ignore */ }
            }
            if (!"PIS_DONE".equals(item.getStatus())) {
                allDone = false;
            }
        }

        ProductionRequest pr = prRepository.findById(prId).orElseThrow();

        if (hasProducing) {
            pr.changeStatus("PR_PRODUCING");
        } else if (allDone) {
            pr.changeStatus("PR_COMPLETED");
        } else if (hasTarget) {
            pr.changeStatus("PR_PLANNING");
        } else if (hasPlanned) {
            pr.changeStatus("PR_PLANNED");
        }
    }

}
