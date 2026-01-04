package com.werp.sero.order.command.application.service;

import com.werp.sero.order.command.application.dto.SOGoalCreateRequestDTO;
import com.werp.sero.order.command.application.dto.SOGoalUpdateRequestDTO;
import com.werp.sero.order.command.application.dto.SOGoalResponseDTO;

public interface SODashboardCommandService {
    SOGoalResponseDTO createMonthlyGoal(final SOGoalCreateRequestDTO request);

    SOGoalResponseDTO updateMonthlyGoal(final int goalId, final long goalAmount);
}
