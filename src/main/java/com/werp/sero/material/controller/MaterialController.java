package com.werp.sero.material.controller;

import com.werp.sero.common.security.AccessType;
import com.werp.sero.common.security.RequirePermission;
import com.werp.sero.material.dto.MaterialCreateRequestDTO;
import com.werp.sero.material.dto.MaterialDetailResponseDTO;
import com.werp.sero.material.dto.MaterialListResponseDTO;
import com.werp.sero.material.dto.MaterialUpdateRequestDTO;
import com.werp.sero.material.service.MaterialService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "자재 관리", description = "자재 조회, 등록, 수정, 비활성화 API")
@RestController
@RequestMapping("/materials")
@RequiredArgsConstructor
public class MaterialController {

    private final MaterialService materialService;

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

        return materialService.getMaterialList(type, status, keyword);
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
        return materialService.getMaterialDetail(id);
    }

    /**
     * 신규 자재 등록
     *
     * POST /materials
     *
     * @param request 자재 등록 요청 정보 (자재명, 자재코드, 유형, 단가, 설명 등)
     */
    @Operation(
            summary = "자재 등록",
            description = "신규 자재를 등록합니다."
    )
    @PostMapping
    @RequirePermission(menu = "MM_MAT", authorities = {"AC_SYS", "AC_SAL", "AC_PRO"}, accessType = AccessType.WRITE)
    public void createMaterial(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "자재 등록 요청 정보",
                    required = true
            )
            @RequestBody MaterialCreateRequestDTO request) {
        materialService.createMaterial(request, 5); // 임시 로그인 유저(생산팀)
    }

    /**
     * 기존 자재 정보 수정
     *
     * PUT /materials/{id}
     *
     * @param id 수정할 자재 ID (예: 7)
     * @param request 자재 수정 요청 정보 (자재명, 단가, 상태, 설명 등)
     */
    @Operation(
            summary = "자재 수정",
            description = "기존 자재 정보를 수정합니다."
    )
    @PutMapping("/{id}")
    @RequirePermission(menu = "MM_MAT", authorities = {"AC_SYS", "AC_SAL", "AC_PRO"}, accessType = AccessType.WRITE)
    public void updateMaterial(
            @Parameter(description = "자재 ID", example = "7")
            @PathVariable int id,

            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "자재 수정 요청 정보",
                    required = true
            )
            @RequestBody MaterialUpdateRequestDTO request) {

        materialService.updateMaterial(id, request);
    }

    /**
     * 자재 비활성화 처리
     *
     * PATCH /materials/{id}/deactivate
     *
     * @param id 비활성화할 자재 ID (예: 7)
     * @apiNote 실제 삭제가 아닌 비활성화 처리(soft delete)로, 데이터는 유지됩니다.
     */
    @Operation(
            summary = "자재 비활성화",
            description = "자재를 비활성화 처리합니다. (실삭제 아님)"
    )
    @PatchMapping("/{id}/deactivate")
    @RequirePermission(menu = "MM_MAT", authorities = {"AC_SYS", "AC_SAL", "AC_PRO"}, accessType = AccessType.WRITE)
    public void deactivate(
            @Parameter(description = "자재 ID", example = "7")
            @PathVariable int id) {
        materialService.deactivateMaterial(id);
    }
}
