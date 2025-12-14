package com.werp.sero.material.controller;

import com.werp.sero.material.dto.BomExplosionRequestDTO;
import com.werp.sero.material.dto.BomExplosionResponseDTO;
import com.werp.sero.material.dto.BomImplosionResponseDTO;
import com.werp.sero.material.service.BomCalculationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * BOM 소요량 계산 컨트롤러
 */
@RestController
@RequestMapping("/api/bom")
@RequiredArgsConstructor
public class BomCalculationController {

    private final BomCalculationService bomCalculationService;

    /**
     * 정전개: 완제품 생산에 필요한 원부자재 소요량 계산
     *
     * POST /api/bom/explosion
     * {
     *   "materialId": 7,    // 완제품 ID (예: 브레이크 패드 어셈블리)
     *   "quantity": 100     // 생산 수량
     * }
     *
     * @param request 완제품 ID와 생산 수량
     * @return 필요한 원부자재 목록 및 총 소요량
     */
    @PostMapping("/explosion")
    public BomExplosionResponseDTO calculateExplosion(@RequestBody BomExplosionRequestDTO request) {
        return bomCalculationService.calculateExplosion(request);
    }

    /**
     * 역전개: 특정 원부자재를 사용하는 완제품 목록 조회
     *
     * GET /api/bom/implosion/{rawMaterialId}
     *
     * @param rawMaterialId 원부자재 ID (예: 1 - 마찰재 패드)
     * @return 해당 원자재를 사용하는 완제품 목록
     */
    @GetMapping("/implosion/{rawMaterialId}")
    public BomImplosionResponseDTO calculateImplosion(@PathVariable int rawMaterialId) {
        return bomCalculationService.calculateImplosion(rawMaterialId);
    }
}
