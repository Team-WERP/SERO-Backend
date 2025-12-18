package com.werp.sero.production.command.application.service;

import com.werp.sero.employee.command.domain.aggregate.Employee;
import com.werp.sero.employee.command.domain.repository.EmployeeRepository;
import com.werp.sero.order.command.domain.aggregate.SalesOrder;
import com.werp.sero.order.command.domain.aggregate.SalesOrderItem;
import com.werp.sero.order.command.domain.repository.SORepository;
import com.werp.sero.order.command.domain.repository.SOItemRepository;
import com.werp.sero.order.exception.SalesOrderItemNotFoundException;
import com.werp.sero.order.exception.SalesOrderNotFoundException;
import com.werp.sero.production.command.application.dto.PRDraftCreateRequestDTO;
import com.werp.sero.production.command.application.dto.PRItemCreateRequestDTO;
import com.werp.sero.production.command.domain.aggregate.ProductionRequest;
import com.werp.sero.production.command.domain.aggregate.ProductionRequestItem;
import com.werp.sero.production.command.domain.repository.PRItemRepository;
import com.werp.sero.production.command.domain.repository.PRRepository;
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

        String prCode = documentSequenceCommandService.generateDocumentCode("DOC_PR");

        ProductionRequest pr = ProductionRequest.createDraft(
                prCode,
                so,
                drafter,
                dto.getDueAt(),
                dto.getReason()
        );

        prRepository.save(pr);

        if(dto.getItems() != null) {
            for(PRItemCreateRequestDTO itemDto : dto.getItems()) {
                SalesOrderItem soItem = soItemRepository.findById(itemDto.getSoItemId())
                        .orElseThrow(SalesOrderItemNotFoundException::new);
                ProductionRequestItem item =
                        pr.addItem(soItem, itemDto.getQuantity());
                prtItemRepository.save(item);
            }
        }

        return pr.getId();
    }
}
