package com.werp.sero.shipping.command.application.service;

import com.werp.sero.common.file.S3Uploader;
import com.werp.sero.common.pdf.DetailedHtmlTemplateGenerator;
import com.werp.sero.common.pdf.PdfGenerator;
import com.werp.sero.employee.command.domain.aggregate.Employee;
import com.werp.sero.order.command.domain.aggregate.SalesOrder;
import com.werp.sero.order.command.domain.aggregate.SalesOrderItem;
import com.werp.sero.order.command.domain.aggregate.SalesOrderItemHistory;
import com.werp.sero.order.command.domain.repository.SOItemRepository;
import com.werp.sero.order.command.domain.repository.SORepository;
import com.werp.sero.order.command.domain.repository.SalesOrderItemHistoryRepository;
import com.werp.sero.order.exception.SalesOrderItemNotFoundException;
import com.werp.sero.order.exception.SalesOrderNotFoundException;
import com.werp.sero.shipping.command.application.dto.DOCreateRequestDTO;
import com.werp.sero.shipping.command.application.dto.DOItemRequestDTO;
import com.werp.sero.shipping.command.domain.aggregate.DeliveryOrder;
import com.werp.sero.shipping.command.domain.aggregate.DeliveryOrderItem;
import com.werp.sero.shipping.command.domain.repository.DeliveryOrderItemRepository;
import com.werp.sero.shipping.command.domain.repository.DeliveryOrderRepository;
import com.werp.sero.shipping.query.dto.DODetailResponseDTO;
import com.werp.sero.shipping.query.service.DODetailQueryService;
import com.werp.sero.system.command.application.service.DocumentSequenceCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DeliveryOrderCommandServiceImpl implements DeliveryOrderCommandService {

    private final DeliveryOrderRepository deliveryOrderRepository;
    private final DeliveryOrderItemRepository deliveryOrderItemRepository;
    private final SORepository soRepository;
    private final SOItemRepository soItemRepository;
    private final SalesOrderItemHistoryRepository salesOrderItemHistoryRepository;
    private final DocumentSequenceCommandService documentSequenceCommandService;
    private final DODetailQueryService doDetailQueryService;
    private final PdfGenerator pdfGenerator;
    private final DetailedHtmlTemplateGenerator htmlTemplateGenerator;
    private final S3Uploader s3Uploader;

    @Override
    @Transactional
    public String createDeliveryOrder(DOCreateRequestDTO requestDTO, Employee manager) {
        // 1. 주문 조회
        SalesOrder salesOrder = soRepository.findById(requestDTO.getSoId())
                .orElseThrow(SalesOrderNotFoundException::new);

        // 2. 납품서 코드 생성 (DO-YYYYMMDD-XXX 형식)
        String doCode = documentSequenceCommandService.generateDocumentCode("DOC_DO");

        // 3. 납품서 엔티티 생성
        DeliveryOrder deliveryOrder = DeliveryOrder.builder()
                .doCode(doCode)
                .doUrl("") // TODO: 문서 URL은 추후 처리
                .note(requestDTO.getNote())
                .createdAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .shippedAt(requestDTO.getShippedAt())
                .status("DO_BEFORE_GI") // 기본 상태: 출고지시서 작성 이전
                .salesOrder(salesOrder)
                .manager(manager)
                .build();

        // 4. 납품서 저장
        DeliveryOrder savedDeliveryOrder = deliveryOrderRepository.save(deliveryOrder);

        // 5. 납품서 품목 저장
        List<DeliveryOrderItem> deliveryOrderItems = new ArrayList<>();

        // items가 null이거나 비어있으면 주문의 모든 품목을 자동으로 포함
        if (requestDTO.getItems() == null || requestDTO.getItems().isEmpty()) {
            List<SalesOrderItem> allOrderItems = soItemRepository.findBySalesOrderId(requestDTO.getSoId());

            for (SalesOrderItem salesOrderItem : allOrderItems) {
                DeliveryOrderItem deliveryOrderItem = DeliveryOrderItem.builder()
                        .doQuantity(salesOrderItem.getQuantity()) // 주문 수량 그대로 사용
                        .deliveryOrder(savedDeliveryOrder)
                        .salesOrderItem(salesOrderItem)
                        .build();

                deliveryOrderItems.add(deliveryOrderItem);
            }
        } else {
            // items가 있으면 지정된 품목만 포함
            for (DOItemRequestDTO itemDTO : requestDTO.getItems()) {
                SalesOrderItem salesOrderItem = soItemRepository.findById(itemDTO.getSoItemId())
                        .orElseThrow(SalesOrderItemNotFoundException::new);

                DeliveryOrderItem deliveryOrderItem = DeliveryOrderItem.builder()
                        .doQuantity(itemDTO.getDoQuantity())
                        .deliveryOrder(savedDeliveryOrder)
                        .salesOrderItem(salesOrderItem)
                        .build();

                deliveryOrderItems.add(deliveryOrderItem);
            }
        }

        // 6. 납품서 품목 일괄 저장
        deliveryOrderItemRepository.saveAll(deliveryOrderItems);

        // 7. PDF 생성 및 S3 업로드
        try {
            // 7-1. 완전한 납품서 데이터 조회 (품목 포함)
            DODetailResponseDTO doDetail = doDetailQueryService.getDeliveryOrderDetail(doCode);

            // 7-2. HTML 템플릿 생성
            String htmlContent = htmlTemplateGenerator.generateDeliveryOrderDetailHtml(doDetail);

            // 7-3. PDF 생성
            byte[] pdfBytes = pdfGenerator.generatePdfFromHtml(htmlContent);

            // 7-4. S3 업로드
            String fileName = doCode + ".pdf";
            String doUrl = s3Uploader.uploadPdf("delivery-orders/", pdfBytes, fileName);

            // 7-5. Entity에 URL 저장
            savedDeliveryOrder.updateDoUrl(doUrl);
            deliveryOrderRepository.save(savedDeliveryOrder);
        } catch (Exception e) {
            // PDF 생성 실패 시 로그만 남기고 진행 (핵심 비즈니스 로직은 완료됨)
            System.err.println("납품서 PDF 생성 실패: " + e.getMessage());
        }

        // 7. 주문 품목 이력 생성 (기납품 수량)
        String createdAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        List<SalesOrderItemHistory> histories = new ArrayList<>();

        for (DeliveryOrderItem doItem : deliveryOrderItems) {
            SalesOrderItemHistory history = SalesOrderItemHistory.createForDeliveryOrder(
                    doItem.getSalesOrderItem().getId(),
                    doItem.getDoQuantity(),
                    manager.getId(),
                    createdAt
            );
            histories.add(history);
        }

        salesOrderItemHistoryRepository.saveAll(histories);

        return doCode;
    }
}
