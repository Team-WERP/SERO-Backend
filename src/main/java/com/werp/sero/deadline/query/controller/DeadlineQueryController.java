package com.werp.sero.deadline.query.controller;

import com.werp.sero.deadline.query.dto.DeadLineQueryRequestDTO;
import com.werp.sero.deadline.query.dto.DeadLineQueryResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.werp.sero.deadline.query.service.DeadLineQueryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Tag(name = "고객사 납기 가능 여부 조회", description = "고객사가 주문 시 희망 수령일에 납기 가능 여부 조회 모달")
@RequestMapping("clients/{clientId}")
@RequiredArgsConstructor
@RestController
public class DeadlineQueryController {

    private final DeadLineQueryService deadLineQueryService;

    @Operation(summary = "납기 가능 여부 조회")
    @PostMapping("/check-availability")
    public ResponseEntity<List<DeadLineQueryResponseDTO>> getDeadLine(

            @RequestBody DeadLineQueryRequestDTO request
    ){

        //Service에서 로직 처리
        DeadLineQueryResponseDTO response = deadLineQueryService.calculateDeadLine(request);
        return ResponseEntity.ok().build();
    }

}
