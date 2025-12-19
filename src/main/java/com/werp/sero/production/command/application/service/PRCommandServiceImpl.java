package com.werp.sero.production.command.application.service;

import com.werp.sero.common.error.ErrorCode;
import com.werp.sero.common.error.exception.BusinessException;
import com.werp.sero.employee.command.domain.aggregate.Employee;
import com.werp.sero.order.command.domain.aggregate.SalesOrder;
import com.werp.sero.order.command.domain.aggregate.SalesOrderItem;
import com.werp.sero.order.command.domain.repository.SORepository;
import com.werp.sero.order.command.domain.repository.SOItemRepository;
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
import com.werp.sero.production.exception.ProductionDraftNotFoundException;
import com.werp.sero.production.exception.ProductionItemNotInSalesOrderException;
import com.werp.sero.production.exception.ProductionItemQuantityInvalidException;
import com.werp.sero.production.exception.ProductionNotDraftException;
import com.werp.sero.system.command.application.service.DocumentSequenceCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PRCommandServiceImpl implements PRCommandService {
    private final PRRepository prRepository;
    private final PRItemRepository prtItemRepository;
    private final SORepository soRepository;
    private final SOItemRepository soItemRepository;
    private final DocumentSequenceCommandService documentSequenceCommandService;

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
        int total = 0;
        if (dto.getItems() != null) {
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
        }
        pr.changeTotalQuantity(total);
        prRepository.save(pr);
    }
}
