package com.werp.sero.order.query.dao;

import com.werp.sero.order.query.dto.SOCalendarDTO;
import com.werp.sero.order.query.dto.SODashboardResponseDTO;
import com.werp.sero.order.query.dto.SOUrgentOrderDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SODashboardMapper {
    SODashboardResponseDTO.SummaryStats getSummaryStats(int year, int month, int lastYear, int lastMonth);

    List<SODashboardResponseDTO.ClientTopStats> getTopClients(int year, int month);

    List<SODashboardResponseDTO.SOGoalDTO> getMonthlyGoal(int year, int month);

    List<SOUrgentOrderDTO> getUrgentOrders();

    List<SOCalendarDTO> getCalendarEvents(int year, int month);
}
