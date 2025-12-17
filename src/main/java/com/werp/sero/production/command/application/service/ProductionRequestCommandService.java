package com.werp.sero.production.command.application.service;

import com.werp.sero.production.command.application.dto.ProductionRequestDraftCreateRequestDTO;

public interface ProductionRequestCommandService {
    int createDraft(ProductionRequestDraftCreateRequestDTO dto);
}
