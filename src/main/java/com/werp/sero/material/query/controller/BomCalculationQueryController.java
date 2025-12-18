package com.werp.sero.material.query.controller;

import com.werp.sero.common.security.AccessType;
import com.werp.sero.common.security.RequirePermission;
import com.werp.sero.material.query.dto.BomExplosionRequestDTO;
import com.werp.sero.material.query.dto.BomExplosionResponseDTO;
import com.werp.sero.material.query.dto.BomImplosionResponseDTO;
import com.werp.sero.material.query.dto.MaterialSearchResponseDTO;
import com.werp.sero.material.query.service.BomCalculationQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * BOM 소요량 계산 컨트롤러
 */
@Tag(name = "BOM 계산", description = "BOM 정전개/역전개 및 자재 검색 API")
@RestController
@RequestMapping("/bom")
@RequiredArgsConstructor
public class BomCalculationQueryController {

    private final BomCalculationQueryService bomCalculationQueryService;

    @Operation(
            summary = "자재 검색",
            description = "자재명 또는 자재코드로 품목을 검색합니다."
    )
    @GetMapping("/materials/search")
    @RequirePermission(menu = "MM_MAT", authorities = {"AC_SYS", "AC_SAL", "AC_PRO", "AC_WHS"}, accessType = AccessType.READ)
    public List<MaterialSearchResponseDTO> searchMaterials(
            @Parameter(description = "검색어 (자재명 또는 자재코드)", example = "브레이크")
            @RequestParam(required = false) String keyword,
            @Parameter(description = "자재 유형 (MAT_FG, MAT_RM)", example = "MAT_FG")
            @RequestParam(required = false) String type) {
        return bomCalculationQueryService.searchMaterials(keyword, type);
    }


    @Operation(
            summary = "BOM 정전개",
            description = "완제품 생산 수량 기준으로 원부자재 소요량을 계산합니다."
    )
    @GetMapping("/explosion/{materialId}")
    @RequirePermission(menu = "MM_MAT", authorities = {"AC_SYS", "AC_SAL", "AC_PRO", "AC_WHS"}, accessType = AccessType.READ)
    public BomExplosionResponseDTO calculateExplosion(
            @Parameter(description = "완제품 ID", example = "7", required = true)
            @RequestParam int materialId,
            @Parameter(description = "생산 수량", example = "100", required = true)
            @RequestParam int quantity) {
        BomExplosionRequestDTO request = new BomExplosionRequestDTO(materialId, quantity);
        return bomCalculationQueryService.calculateExplosion(request);
    }

    @Operation(
            summary = "BOM 역전개",
            description = "특정 원부자재를 사용하는 완제품 목록을 조회합니다."
    )
    @GetMapping("/implosion/{rawMaterialId}")
    @RequirePermission(menu = "MM_MAT", authorities = {"AC_SYS", "AC_SAL", "AC_PRO", "AC_WHS"}, accessType = AccessType.READ)
    public BomImplosionResponseDTO calculateImplosion(
            @Parameter(description = "원부자재 ID", example = "1")
            @PathVariable int rawMaterialId) {
        return bomCalculationQueryService.calculateImplosion(rawMaterialId);
    }
}
