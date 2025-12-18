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

    private static final int WORKING_MINUTES_PER_DAY = 480; // 8시간
    private static final int WORK_START_HOUR = 9;           // 근무 시작 시간
    private static final int WORK_END_HOUR = 18;            // 근무 종료 시간
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    @Override
    @Transactional(readOnly = true)
    public List<DeadLineQueryResponseDTO> calculateDeadLine(DeadLineQueryRequestDTO request) {

        List<DeadLineQueryResponseDTO> responses = new ArrayList<>();  // 품목별 응답

        // 각 품목별로 납기 가능 여부 계산
        for (DeadLineQueryRequestDTO.ItemRequest item : request.getItems()) {

            // 1. LineMaterial 조회
            DeadLineMapper.LineMaterialInfo lineMaterial = deadLineMapper
                    .findLineMaterialByMaterialCode(item.getMaterialCode())
//                    .orElseThrow(() -> new RuntimeException("해당 자재를 생산할 수 있는 라인이 없습니다. ")) 예외처리는 앞에서

            // 2. 최신 생산계획 종료일 조회
            String latestEndDateStr = deadLineMapper
                    .findLastestProduction(lineMaterial.getProductionLineId())
                    .orElse(null);

            // 3. 생산 시작 시간 결정 (이번 주문이 들어온다면)
            LocalDateTime productionStartTime;
            if (latestEndDateStr != null) {
                // 기존 생산계획이 있으면, 그 종료일의 다음날 09:00 부터 시작한다고 가정!
                LocalDateTime latestEndDate = LocalDateTime.parse(latestEndDateStr, FORMATTER);
                productionStartTime = latestEndDate.plusDays(1)
                        .withHour(WORK_START_HOUR)
                        .withMinute(0)
                        .withSecond(0);
            } else {
                // 기존 생산계획이 없으면 당장 내일 09:00부터 시작
                String nowDateTimeStr = DateTimeUtils.nowDateTime();
                LocalDateTime now = LocalDateTime.parse(nowDateTimeStr, FORMATTER);
                productionStartTime = now.plusDays(1)
                        .withHour(WORK_START_HOUR)
                        .withMinute(0)
                        .withSecond(0);
            }

            // 4. 생산 소요 시간 계산
            // 개수 x 시간으로 판단
            int totalProductionMinutes = item.getQuantity() * lineMaterial.getCycleTime();



            // 5. 납기 예정일 계산
            LocalDateTime expectedDeliveryDate = calculateExpectedDeliveryDate(
                    productionStartTime,
                    totalProductionMinutes
            );

            // 6. 희망 납기일 파싱
            LocalDateTime desiredDeliveryDate = LocalDateTime.parse(
                    request.getDesiredDeliveryDate(),
                    FORMATTER
            );

            // 7. 납기 가능 여부 판단
            boolean del


        }

    }
}
