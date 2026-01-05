package com.werp.sero.order.query.service;

import com.werp.sero.order.query.dao.SODashboardMapper;
import com.werp.sero.order.query.dto.SOCalendarDTO;
import com.werp.sero.order.query.dto.SODashboardResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SODashboardQueryServiceImpl implements SODashboardQueryService {
    private final SODashboardMapper dashboardMapper;

    @Override
    @Transactional(readOnly = true)
    public SODashboardResponseDTO getDashboardData(final int year, final int month) {
        LocalDate targetDate = LocalDate.of(year, month, 1);
        LocalDate lastMonthDate = targetDate.minusMonths(1);

        int lastYear = lastMonthDate.getYear();
        int lastMonth = lastMonthDate.getMonthValue();

        return SODashboardResponseDTO.builder()
                .stats(dashboardMapper.getSummaryStats(year, month, lastYear, lastMonth))
                .monthlyGoal(dashboardMapper.getMonthlyGoal(year, month))
                .topClients(dashboardMapper.getTopClients(year, month))
                .urgentOrders(dashboardMapper.getUrgentOrders())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public List<SOCalendarDTO> getDashboardCalendar(final int year, final int month) {
        List<SOCalendarDTO> calendarList = dashboardMapper.getCalendarEvents(year, month);

        return calendarList;
    }

}
