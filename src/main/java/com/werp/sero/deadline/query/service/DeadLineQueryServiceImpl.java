package com.werp.sero.deadline.query.service;

import com.werp.sero.common.util.DateTimeUtils;
import com.werp.sero.deadline.query.dao.DeadLineMapper;
import com.werp.sero.deadline.query.dto.DeadLineQueryRequestDTO;
import com.werp.sero.deadline.query.dto.DeadLineQueryResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DeadLineQueryServiceImpl implements DeadLineQueryService {

    private final DeadLineMapper deadLineMapper;
    private static final int SHIPPING_DAYS = 2;             // 배송 소요 일수
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");


    @Override
    @Transactional(readOnly = true)
    public List<DeadLineQueryResponseDTO> calculateDeadLine(DeadLineQueryRequestDTO request) {

        List<DeadLineQueryResponseDTO> responses = new ArrayList<>();  // 품목별 응답

        // 입력 검증
        for (DeadLineQueryRequestDTO.ItemRequest item : request.getItems()) {

            // 1. LineMaterial 조회
            DeadLineMapper.LineMaterialInfo lineMaterial = deadLineMapper
                    .findLineMaterialByMaterialCode(item.getMaterialCode())
                    .get();

            // 5. 생산 완료 예정일 계산
            LocalDateTime productionEndTime = null;

            // 6. 배송 시간 추가하여 최종 납기 예정일 계산 (생산 완료 + 배송 2일)
            LocalDateTime expectedDeliveryDate = productionEndTime.plusDays(SHIPPING_DAYS);

            // 7. 희망 납기일 파싱 (초 단위가 있으면 제거)
            String desiredDateStr = request.getDesiredDeliveryDate();
            if (desiredDateStr.length() > 16 && desiredDateStr.charAt(16) == ':') {
                desiredDateStr = desiredDateStr.substring(0, 16);
            }
            LocalDateTime desiredDeliveryDate = LocalDateTime.parse(desiredDateStr, FORMATTER);

            // 8. 납기 가능 여부 판단 (생산 완료 + 배송까지 고려)
            boolean deliveryPossible = !expectedDeliveryDate.isAfter(desiredDeliveryDate);

            // 9. 응답 DTO 생성
            DeadLineQueryResponseDTO response = new DeadLineQueryResponseDTO(
                    item.getMaterialCode(),
                    request.getDesiredDeliveryDate(),
                    expectedDeliveryDate.format(FORMATTER),
                    deliveryPossible,
                    null
            );

            responses.add(response);
        }

        return responses;
    }

}
