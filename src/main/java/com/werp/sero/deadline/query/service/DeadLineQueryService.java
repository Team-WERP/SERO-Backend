package com.werp.sero.deadline.query.service;

import com.werp.sero.deadline.query.dto.DeadLineQueryRequestDTO;
import com.werp.sero.deadline.query.dto.DeadLineQueryResponseDTO;

import java.util.List;

public interface DeadLineQueryService {

    public List<DeadLineQueryResponseDTO> calculateDeadLine(DeadLineQueryRequestDTO request);
}
