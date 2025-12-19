package com.werp.sero.order.command.application.service;

import com.werp.sero.client.command.domain.aggregate.Client;
import com.werp.sero.client.command.domain.repository.ClientRepository;
import com.werp.sero.client.exception.ClientNotFoundException;
import com.werp.sero.client.query.dto.ClientAddressResponseDTO;
import com.werp.sero.common.util.DateTimeUtils;
import com.werp.sero.employee.command.domain.aggregate.ClientEmployee;
import com.werp.sero.order.command.application.dto.SOClientOrderRequestDTO;
import com.werp.sero.order.command.application.dto.SODetailResponseDTO;
import com.werp.sero.order.command.domain.aggregate.SalesOrder;
import com.werp.sero.order.command.domain.aggregate.SalesOrderItem;
import com.werp.sero.order.command.domain.repository.SOItemRepository;
import com.werp.sero.order.command.domain.repository.SORepository;
import com.werp.sero.order.query.dto.SOClientItemResponseDTO;
import com.werp.sero.system.command.application.service.DocumentSequenceCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SOClientCommandServiceImpl implements SOClientCommandService {

    private final SORepository orderRepository;
    private final SOItemRepository orderItemRepository;
    private final ClientRepository  clientRepository;

    private final DocumentSequenceCommandService documentSequenceCommandService;

    @Transactional
    @Override
    public SODetailResponseDTO createOrder(final ClientEmployee clientEmployee, final SOClientOrderRequestDTO request) {

        String generatedSoCode = documentSequenceCommandService.generateDocumentCode("DOC_SO");

        long totalOrderPrice = request.getItems().stream()
                .mapToLong(SOClientItemResponseDTO::getTotalPrice).sum();

        int totalOrderQuantity = request.getItems().stream()
                .mapToInt(SOClientItemResponseDTO::getQuantity).sum();
        
        String mainItemName = request.getItems().get(0).getItemName();

        ClientAddressResponseDTO address = request.getAddress();

        // 주문 정보 추가
        SalesOrder salesOrder = SalesOrder.builder()
                .soCode(generatedSoCode)
                .poCode(request.getPoCode())
                .client(clientEmployee.getClient())
                .clientEmployee(clientEmployee)
                .clientName(clientEmployee.getClient().getCompanyName())
                .orderedAt(DateTimeUtils.nowDateTime())
                .shippedAt(request.getShipped_at())
                .totalPrice(totalOrderPrice)
                .totalQuantity(totalOrderQuantity)
                .totalItemCount(request.getItems().size())
                .mainItemName(mainItemName)

                .shippingName(address.getName())
                .address(address.getAddress())
                .recipientName(address.getRecipientName())
                .recipientContact(address.getRecipientContact())

                .status("ORD_RED")
                .note(request.getNote())
                .build();

        SalesOrder savedOrder = orderRepository.save(salesOrder);

        // 주문 품목 추가
        List<SalesOrderItem> orderItems = request.getItems().stream()
                .map(itemDto -> SalesOrderItem.builder()
                        .salesOrder(savedOrder)
                        .itemCode(itemDto.getItemCode())
                        .itemName(itemDto.getItemName())
                        .spec(itemDto.getSpec())
                        .quantity(itemDto.getQuantity())
                        .unit(itemDto.getUnit())
                        .unitPrice(itemDto.getUnitPrice())
                        .totalPrice(itemDto.getTotalPrice())
                        .build())
                .collect(Collectors.toList());

        orderItemRepository.saveAll(orderItems);

        Client client = clientRepository.findById(clientEmployee.getClient().getId())
                .orElseThrow(ClientNotFoundException::new);

        // 미수금 추가
        client.addReceivables(totalOrderPrice);

        return SODetailResponseDTO.of(savedOrder);
    }
}
