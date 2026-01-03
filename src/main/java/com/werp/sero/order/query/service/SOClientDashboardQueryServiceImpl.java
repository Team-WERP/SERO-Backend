package com.werp.sero.order.query.service;

import com.werp.sero.order.query.dao.SOClientDashboardMapper;
import com.werp.sero.order.query.dto.SOClientDashboardResponseDTO;
import com.werp.sero.order.query.dto.SOClientListResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SOClientDashboardQueryServiceImpl implements SOClientDashboardQueryService {

    private final SOClientDashboardMapper dashboardMapper;

    @Override
    @Transactional(readOnly = true)
    public SOClientDashboardResponseDTO getDashboardData(final int clientId) {

        SOClientDashboardResponseDTO.SOClientStatDTO stats = dashboardMapper.selectClientDashboardStats(clientId);
        List<SOClientDashboardResponseDTO.SOClientNoticeDTO> notices = dashboardMapper.selectClientNotices();
        List<SOClientDashboardResponseDTO.SOClientDashboardListDTO> orderList = dashboardMapper.selectRecentOrders(clientId);
        List<SOClientDashboardResponseDTO.SOClientUrgentOrderDTO> urgentOrders = dashboardMapper.getClientUrgentOrders(clientId);

        List<SOClientDashboardResponseDTO.SOClientDashboardListDTO> convertedOrders = orderList.stream()
                .map(order -> SOClientDashboardResponseDTO.SOClientDashboardListDTO.builder()
                        .orderId(order.getOrderId())
                        .soCode(order.getSoCode())
                        .mainItemName(order.getMainItemName())
                        .totalItemCount(order.getTotalItemCount())
                        .totalQuantity(order.getTotalQuantity())
                        .shippedAt(order.getShippedAt())
                        .status(SOClientListResponseDTO.convertStatus(order.getStatus()))
                        .build())
                .toList();

        List<SOClientDashboardResponseDTO.SOClientUrgentOrderDTO> convertedUrgentOrders = urgentOrders.stream()
                .map(order -> SOClientDashboardResponseDTO.SOClientUrgentOrderDTO.builder()
                        .orderId(order.getOrderId())
                        .orderCode(order.getOrderCode())
                        .poCode(order.getPoCode())
                        .shippedAt(order.getShippedAt())
                        .dDay(order.getDDay())
                        .status(SOClientListResponseDTO.convertStatus(order.getStatus()))
                        .build())
                .toList();

        return SOClientDashboardResponseDTO.builder()
                .stats(stats)
                .notices(notices)
                .orderList(convertedOrders)
                .urgentOrders(convertedUrgentOrders)
                .build();
    }
}
