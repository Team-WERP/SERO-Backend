package com.werp.sero.production.command.application.controller;

import com.werp.sero.production.command.application.dto.ProductionPlanValidateRequestDTO;
import com.werp.sero.production.command.application.dto.ProductionPlanValidationResponseDTO;
import com.werp.sero.production.command.application.service.PPCommandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/production-plans")
@Tag(
        name = "생산계획 - Command",
        description = "생산계획 검증 및 생성 API"
)
@RequiredArgsConstructor
public class PPCommandController {
    private final PPCommandService ppCommandService;

    @Operation(
            summary = "생산계획 검증",
            description = """
                    생산요청 품목(PR Item)을 기준으로 선택한 생산라인, 기간, 수량이 유효한지 검증합니다.

                    검증 항목:
                    - PR Item 존재 여부
                    - 이미 생산계획 존재 여부 (PR Item당 1회 정책)
                    - 자재 마스터 존재 여부
                    - 선택한 생산라인에서 생산 가능 여부
                    - 생산 기간 유효성
                    - 생산 가능 CAPA 대비 수량 검증
                    """
    )
    @PostMapping("/validate")
    public ResponseEntity<ProductionPlanValidationResponseDTO> validate(
            @RequestBody ProductionPlanValidateRequestDTO request
            ) {
        return ResponseEntity.ok(
                ppCommandService.validate(request)
        );
    }
}
