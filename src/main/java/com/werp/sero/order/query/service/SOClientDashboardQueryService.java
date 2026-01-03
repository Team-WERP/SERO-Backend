package com.werp.sero.order.query.service;

import com.werp.sero.order.query.dto.SOClientDashboardResponseDTO;

public interface SOClientDashboardQueryService {
    SOClientDashboardResponseDTO getDashboardData(final int clientId);
}
