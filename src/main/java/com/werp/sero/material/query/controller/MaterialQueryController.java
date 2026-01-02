package com.werp.sero.material.query.controller;

import com.werp.sero.common.security.AccessType;
import com.werp.sero.common.security.RequirePermission;
import com.werp.sero.common.swagger.ApiErrorResponses;
import com.werp.sero.material.query.dto.MaterialDetailResponseDTO;
import com.werp.sero.material.query.dto.MaterialListResponseDTO;
import com.werp.sero.material.query.service.MaterialQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "자재 관리 - Query", description = "자재 조회 API")
@RestController
@RequestMapping("/materials")
@RequiredArgsConstructor
public class MaterialQueryController {

    private final MaterialQueryService materialQueryService;

    @Operation(
            summary = "자재 목록 조회",
            description = "조건에 따라 자재 목록을 조회합니다."
    )
    @GetMapping
    @RequirePermission(menu = "MM_MAT", authorities = {"AC_SYS", "AC_SAL", "AC_PRO", "AC_WHS"}, accessType = AccessType.READ)
    public List<MaterialListResponseDTO> getMaterials(
            @Parameter(description = "자재 유형 (MAT_FG: 완제품, MAT_RM: 원부자재)", example = "MAT_FG")
            @RequestParam(required = false) String type,

            @Parameter(description = "자재 상태 (MAT_NORMAL: 정상, MAT_STOP_PREP: 판매 중단 예정, MAT_STOP: 판매 중단, MAT_DISCONTINUED: 단종)", example = "MAT_NORMAL")
            @RequestParam(required = false) String status,

            @Parameter(description = "검색어 (자재명 또는 자재코드)", example = "브레이크")
            @RequestParam(required = false) String keyword) {

        return materialQueryService.getMaterialList(type, status, keyword);
    }

    @Operation(summary = "자재 상세 조회", description = "자재 ID 기준으로 상세 정보를 조회합니다.")
    @ApiErrorResponses.MaterialNotFound
    @GetMapping("/{id}")
    @RequirePermission(menu = "MM_MAT", authorities = {"AC_SYS", "AC_SAL", "AC_PRO", "AC_WHS"}, accessType = AccessType.READ)
    public MaterialDetailResponseDTO getMaterial(
            @Parameter(description = "자재 ID", example = "7")
            @PathVariable int id) {
        return materialQueryService.getMaterialDetail(id);
    }
}
