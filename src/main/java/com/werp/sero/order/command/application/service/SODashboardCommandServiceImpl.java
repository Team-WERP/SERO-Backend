package com.werp.sero.order.command.application.service;

import com.werp.sero.order.command.application.dto.SOGoalCreateRequestDTO;
import com.werp.sero.order.command.application.dto.SOGoalUpdateRequestDTO;
import com.werp.sero.order.command.application.dto.SOGoalResponseDTO;
import com.werp.sero.order.command.domain.aggregate.SalesOrderGoal;
import com.werp.sero.order.command.domain.repository.SOGoalRepository;
import com.werp.sero.order.exception.SalesOrderMonthlyGoalNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class SODashboardCommandServiceImpl implements SODashboardCommandService {
    private final SOGoalRepository goalRepository;

    @Override
    @Transactional
    public SOGoalResponseDTO createMonthlyGoal(final SOGoalCreateRequestDTO request) {
        SalesOrderGoal goal = SalesOrderGoal.builder()
                .goalYear(request.getGoalYear())
                .goalMonth(request.getGoalMonth())
                .goalAmount(request.getGoalAmount())
                .build();

        SalesOrderGoal savedGoal = goalRepository.save(goal);
        return SOGoalResponseDTO.of(savedGoal);
    }

    @Override
    @Transactional
    public SOGoalResponseDTO updateMonthlyGoal(final int goalId, final long goalAmount) {
        SalesOrderGoal goal = goalRepository.findById(goalId)
                .orElseThrow(SalesOrderMonthlyGoalNotFoundException::new);

        goal.updateAmount(goalAmount);

        return SOGoalResponseDTO.of((goal));
    }
}
