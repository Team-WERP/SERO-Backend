package com.werp.sero.order.query.service;

import com.werp.sero.order.query.dto.SOCalendarDTO;
import com.werp.sero.order.query.dto.SODashboardResponseDTO;

import java.util.List;

public interface SODashboardQueryService {
    SODashboardResponseDTO getDashboardData(final int year, final int month);

    List<SOCalendarDTO> getDashboardCalendar(final int year, final int month);
}
