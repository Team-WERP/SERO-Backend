package com.werp.sero.order.query.dao;

import com.werp.sero.order.query.dto.SOClientDashboardResponseDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SOClientDashboardMapper {
    SOClientDashboardResponseDTO.SOClientStatDTO selectClientDashboardStats(final int clientId);

    List<SOClientDashboardResponseDTO.SOClientNoticeDTO> selectClientNotices();

    List<SOClientDashboardResponseDTO.SOClientDashboardListDTO> selectRecentOrders(final int clientId);
}
