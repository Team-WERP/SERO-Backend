package com.werp.sero.material.query.controller;

import com.werp.sero.common.security.AccessType;
import com.werp.sero.common.security.RequirePermission;
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

    /**
     * 자재 목록 조회 (필터링 및 검색 지원)
     *
     * GET /materials?type=MAT_FG&status=MAT_NORMAL&keyword=브레이크
     *
     * @param type 자재 유형 (선택) - MAT_FG(완제품), MAT_RM(원부자재), null(전체)
     * @param status 자재 상태 (선택) - MAT_NORMAL(정상), MAT_STOP(중단), null(전체)
     * @param keyword 검색어 (선택) - 자재명 또는 자재코드로 검색
     * @return 조건에 맞는 자재 목록 (자재 정보, 단가, 상태 정보 포함)
     */
    @Operation(
            summary = "자재 목록 조회",
            description = "조건에 따라 자재 목록을 조회합니다."
    )
    @GetMapping
    @RequirePermission(menu = "MM_MAT", authorities = {"AC_SYS", "AC_SAL", "AC_PRO", "AC_WHS"}, accessType = AccessType.READ)
    public List<MaterialListResponseDTO> getMaterials(
            @Parameter(description = "자재 유형 (MAT_FG: 완제품, MAT_RM: 원부자재)", example = "MAT_FG")
            @RequestParam(required = false) String type,

            @Parameter(description = "자재 상태 (MAT_NORMAL, MAT_STOP 등)", example = "MAT_NORMAL")
            @RequestParam(required = false) String status,

            @Parameter(description = "검색어 (자재명 또는 자재코드)", example = "브레이크")
            @RequestParam(required = false) String keyword) {

        return materialQueryService.getMaterialList(type, status, keyword);
    }

    /**
     * 자재 상세 조회
     *
     * GET /materials/{id}
     *
     * @param id 자재 ID (예: 7)
     * @return 자재 상세 정보 (기본 정보, 단가, 상태, BOM 관계, 재고 정보 등)
     */
    @Operation(
            summary = "자재 상세 조회",
            description = "자재 ID 기준으로 상세 정보를 조회합니다."
    )
    @GetMapping("/{id}")
    @RequirePermission(menu = "MM_MAT", authorities = {"AC_SYS", "AC_SAL", "AC_PRO", "AC_WHS"}, accessType = AccessType.READ)
    public MaterialDetailResponseDTO getMaterial(
            @Parameter(description = "자재 ID", example = "7")
            @PathVariable int id) {
        return materialQueryService.getMaterialDetail(id);
    }
}
