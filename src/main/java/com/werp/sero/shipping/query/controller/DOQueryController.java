package com.werp.sero.shipping.query.controller;

import com.werp.sero.shipping.query.dto.DODetailResponseDTO;
import com.werp.sero.shipping.query.service.DODetailQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/delivery-orders")
@RequiredArgsConstructor
@Tag(name = "DeliveryOrder", description = "납품서 관리 API")
public class DOQueryController {

    private final DODetailQueryService DODetailQueryService;

    @GetMapping("/{doCode}")
    @Operation(summary = "납품서 상세 조회", description = "납품서 코드로 납품서 상세 정보를 조회합니다. (미리보기용)")
    public ResponseEntity<DODetailResponseDTO> getDeliveryOrderDetail(
            @Parameter(description = "납품서 코드", example = "DO-20251219-01")
            @PathVariable String doCode
    ) {
        DODetailResponseDTO response = DODetailQueryService.getDeliveryOrderDetail(doCode);
        return ResponseEntity.ok(response);
    }
}
