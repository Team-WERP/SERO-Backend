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
    private static final int SHIPPING_DAYS = 2;             // 배송 소요 일수
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");


    @Override
    @Transactional(readOnly = true)
    public List<DeadLineQueryResponseDTO> calculateDeadLine(DeadLineQueryRequestDTO request) {

        List<DeadLineQueryResponseDTO> responses = new ArrayList<>();  // 품목별 응답

        // 각 품목별로 납기 가능 여부 계산
        for (DeadLineQueryRequestDTO.ItemRequest item : request.getItems()) {

            // 1. LineMaterial 조회
            DeadLineMapper.LineMaterialInfo lineMaterial = deadLineMapper
                    .findLineMaterialByMaterialCode(item.getMaterialCode())
                    .get();

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
                LocalDateTime now = LocalDateTime.now();
                productionStartTime = now.plusDays(1)
                        .withHour(WORK_START_HOUR)
                        .withMinute(0)
                        .withSecond(0);
            }

            // 4. 생산 소요 시간 계산
            // 개수 x 시간으로 판단
            int totalProductionMinutes = item.getQuantity() * lineMaterial.getCycleTime();



            // 5. 생산 완료 예정일 계산
            LocalDateTime productionEndTime = calculateProductionEndTime(
                    productionStartTime,
                    totalProductionMinutes
            );

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

    /**
     * 생산 시작 시간과 총 소요 시간을 기반으로 생산 완료 시간 계산
     * 근무 시간(09:00 ~ 18:00)만 고려하여 계산
     *
     * 예시:
     * - 시작: 2025-01-15 15:00, 소요: 600분(10시간)
     * - 1일차: 15:00~18:00 = 180분 소진, 남은 시간 420분
     * - 2일차: 09:00~16:00 = 420분 소진, 완료
     * - 결과: 2025-01-16 16:00:00 (생산 완료 시간)
     *
     * @param startTime 생산 시작 시간
     * @param totalMinutes 총 생산 소요 시간(분)
     * @return 생산 완료 예정 시간
     */
    private LocalDateTime calculateProductionEndTime(LocalDateTime startTime, int totalMinutes) {
        // 계산용 변수 초기화
        LocalDateTime calculatingTime = startTime;   // 현재 계산 중인 시간 (생산 진행 중)
        int remainingMinutes = totalMinutes;         // 아직 처리해야 할 남은 작업 시간

        // 남은 작업 시간이 0이 될 때까지 반복
        while (remainingMinutes > 0) {
            int currentHour = calculatingTime.getHour();       // 현재 시간 (0~23)
            int currentMinute = calculatingTime.getMinute();   // 현재 분 (0~59)

            // 근무시간 전이라면 당일 09:00으로 조정
            // 예: 07:30 -> 09:00으로 점프 (아직 작업 시간 소진 안 함)
            if (currentHour < WORK_START_HOUR) {
                calculatingTime = calculatingTime.withHour(WORK_START_HOUR).withMinute(0).withSecond(0);
                continue;  // 다음 반복으로 (시간 조정만 하고 작업 시간은 소진 안 함)
            }

            // 근무시간 이후라면 다음날 09:00으로 조정
            // 예: 19:30 -> 다음날 09:00으로 점프
            if (currentHour >= WORK_END_HOUR) {
                calculatingTime = calculatingTime.plusDays(1).withHour(WORK_START_HOUR).withMinute(0).withSecond(0);
                continue;  // 다음 반복으로 (시간 조정만 하고 작업 시간은 소진 안 함)
            }

            // 오늘 남은 근무 시간 계산 (분)
            // 예: 현재 14:30, 퇴근 18:00 -> (18-14)*60 - 30 = 210분 (3시간 30분)
            int minutesUntilEndOfDay = (WORK_END_HOUR - currentHour) * 60 - currentMinute;

            // 케이스 A: 오늘 남은 근무 시간 내에 작업 완료 가능
            if (remainingMinutes <= minutesUntilEndOfDay) {
                // 남은 작업 시간만큼 더하고 완료
                // 예: 14:30 + 120분 = 16:30
                calculatingTime = calculatingTime.plusMinutes(remainingMinutes);
                remainingMinutes = 0;  // 작업 완료, 반복문 종료
            }
            // 케이스 B: 오늘 안에 완료 불가, 다음날로 넘어감
            else {
                // 오늘 남은 시간만큼만 소진
                // 예: 남은 작업 600분, 오늘 남은 시간 180분 -> 420분은 내일 처리
                remainingMinutes -= minutesUntilEndOfDay;
                // 다음날 09:00으로 설정하고 반복 계속
                calculatingTime = calculatingTime.plusDays(1).withHour(WORK_START_HOUR).withMinute(0).withSecond(0);
            }
        }

        // 생산 완료 시간 반환 
        return calculatingTime;
    }
}
