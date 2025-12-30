package com.werp.sero.deadline.query.service;

import com.werp.sero.deadline.query.dao.DeadLineMapper;
import com.werp.sero.deadline.query.dto.DeadLineQueryRequestDTO;
import com.werp.sero.deadline.query.dto.DeadLineQueryResponseDTO;
import com.werp.sero.deadline.query.dto.LineMaterialInfo;
import com.werp.sero.production.query.dao.PPValidateMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeadLineQueryServiceImpl implements DeadLineQueryService {

    private final DeadLineMapper deadLineMapper;
    private final PPValidateMapper ppValidateMapper;
    private static final int SHIPPING_DAYS = 2;             // 배송 소요 일수
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");


    @Override
    @Transactional(readOnly = true)
    public List<DeadLineQueryResponseDTO> calculateDeadLine(DeadLineQueryRequestDTO request) {

        List<DeadLineQueryResponseDTO> responses = new ArrayList<>();  // 품목별 응답

        // 입력 검증
        String desiredDateStr = normalizeToMinute(request.getDesiredDeliveryDate());
        if (desiredDateStr == null || desiredDateStr.length() < 16) {
            for (DeadLineQueryRequestDTO.ItemRequest item : request.getItems()) {

                responses.add(new DeadLineQueryResponseDTO(
                        item.getMaterialCode(),
                        request.getDesiredDeliveryDate(),
                        null,
                        false,
                        "희망 납기일 형식이 올바르지 않습니다. (yyyy-MM-dd HH:mm)"
                ));
            }
            return responses;
        }

        LocalDateTime desiredDeliveryDateTime;
        try {
            desiredDeliveryDateTime = LocalDateTime.parse(desiredDateStr, FORMATTER);
        } catch (Exception e) {
            for (DeadLineQueryRequestDTO.ItemRequest item : request.getItems()) {
                responses.add(new DeadLineQueryResponseDTO(
                        item.getMaterialCode(),
                        request.getDesiredDeliveryDate(),
                        null,
                        false,
                        "희망 납기일 파싱에 실패했습니다. (yyyy-MM-dd HH:mm)"
                ));
            }
            return responses;
        }

        // 생산 마감일(날짜 단위): 희망 납기일 - 배송일수
        LocalDate productionDeadlineDate = desiredDeliveryDateTime.toLocalDate().minusDays(SHIPPING_DAYS);

        LocalDate today = LocalDate.now();
        LocalDate startDate = today;

        // 마감일이 오늘보다 과거면: 어떤 품목이든 생산 불가 (배송 고려)
        if (productionDeadlineDate.isBefore(today)) {
            for (DeadLineQueryRequestDTO.ItemRequest item : request.getItems()) {
                responses.add(new DeadLineQueryResponseDTO(
                        item.getMaterialCode(),
                        request.getDesiredDeliveryDate(),
                        null,
                        false,
                        "희망 납기일이 너무 임박하여(배송기간 고려) 생산이 불가능합니다."
                ));
            }
            return responses;
        }

        // 2) 품목별 시뮬레이션
        for (DeadLineQueryRequestDTO.ItemRequest item : request.getItems()) {
            String materialCode = item.getMaterialCode();
            int orderQty = item.getQuantity();

            if (orderQty <= 0) {
                responses.add(new DeadLineQueryResponseDTO(
                        materialCode,
                        request.getDesiredDeliveryDate(),
                        null,
                        false,
                        "주문 수량은 1 이상이어야 합니다."
                ));
                continue;
            }

            LineMaterialInfo info = deadLineMapper
                    .findLineMaterialByMaterialCode(materialCode)
                    .orElse(null);

            if (info == null) {
                responses.add(new DeadLineQueryResponseDTO(
                        materialCode,
                        request.getDesiredDeliveryDate(),
                        null,
                        false,
                        "해당 제품의 생산라인이 등록되어 있지 않습니다."
                ));
                continue;
            }

            int lineId = info.getProductionLineId();
            int dailyCapacity = info.getDailyCapacity();

            if (dailyCapacity <= 0) {
                responses.add(new DeadLineQueryResponseDTO(
                        materialCode,
                        request.getDesiredDeliveryDate(),
                        null,
                        false,
                        "생산라인의 일일 생산능력(daily_capacity)이 0 이하입니다."
                ));
                continue;
            }

            int remainingQty = orderQty;
            LocalDate finishDate = null;

            for (LocalDate d = startDate; !d.isAfter(productionDeadlineDate); d = d.plusDays(1)) {

                int plannedQty = ppValidateMapper.sumDailyPlannedQty(lineId, d.toString());
                int available = Math.max(0, dailyCapacity - plannedQty);

                // available이 remainingQty보다 클 수 있으니 의미상 깔끔하게 캡
                int used = Math.min(available, remainingQty);
                remainingQty -= used;

                log.info(
                        "[DEADLINE TEST] material={}, line={}, date={}, daily={}, planned={}, available={}, remaining={}",
                        materialCode, lineId, d, dailyCapacity, plannedQty, available, remainingQty
                );

                if (remainingQty <= 0) {
                    finishDate = d;
                    break;
                }
            }

            boolean deliverable = (finishDate != null);
            String expectedDeliveryDateStr = null;
            String errorMessage = null;

            if (deliverable) {
                LocalDate expectedDeliveryDate = finishDate.plusDays(SHIPPING_DAYS);
                expectedDeliveryDateStr = expectedDeliveryDate.atStartOfDay().format(FORMATTER);
            } else {
                errorMessage = "마감일까지 생산 CAPA가 부족합니다.";
            }

            responses.add(new DeadLineQueryResponseDTO(
                    materialCode,
                    request.getDesiredDeliveryDate(),
                    expectedDeliveryDateStr,
                    deliverable,
                    errorMessage
            ));
        }

        return responses;
    }


    /**
     * "yyyy-MM-dd HH:mm:ss" 같이 초가 포함된 값이 들어와도
     * "yyyy-MM-dd HH:mm"로 안전하게 잘라서 파싱되도록 정규화
     */
    private String normalizeToMinute(String dateTimeStr) {
        if (dateTimeStr == null) return null;
        // yyyy-MM-dd HH:mm 까지 = 16자리
        if (dateTimeStr.length() >= 16) {
            return dateTimeStr.substring(0, 16);
        }
        return dateTimeStr;
    }
}
