package com.werp.sero.material.command.application.controller;

import com.werp.sero.common.security.AccessType;
import com.werp.sero.common.security.RequirePermission;
import com.werp.sero.common.swagger.ApiErrorResponses;
import com.werp.sero.material.command.application.dto.MaterialCreateRequestDTO;
import com.werp.sero.material.command.application.dto.MaterialCreateResponseDTO;
import com.werp.sero.material.command.application.dto.MaterialUpdateRequestDTO;
import com.werp.sero.material.command.application.service.MaterialCommandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Tag(name = "자재 관리 - Command", description = "자재 등록, 수정, 비활성화 API")
@RestController
@RequestMapping("/materials")
@RequiredArgsConstructor
public class MaterialCommandController {

    private final MaterialCommandService materialCommandService;

    /**
     * 신규 자재 등록
     *
     * POST /materials
     *
     * @param request 자재 등록 요청 정보 (자재명, 자재코드, 유형, 단가, 설명 등)
     * @return 생성된 자재 정보
     */
    @Operation(summary = "자재 등록", description = "신규 자재를 등록합니다.")
    @ApiErrorResponses.MaterialCodeDuplicated
    @PostMapping
    @RequirePermission(menu = "MM_MAT", authorities = {"AC_SYS", "AC_SAL", "AC_PRO"}, accessType = AccessType.WRITE)
    public ResponseEntity<MaterialCreateResponseDTO> createMaterial(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "자재 등록 요청 정보",
                    required = true
            )
            @RequestBody MaterialCreateRequestDTO request) {
        MaterialCreateResponseDTO response = materialCommandService.createMaterial(request, 5); // 임시 로그인 유저(생산팀)
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    /**
     * 기존 자재 정보 수정
     *
     * PUT /materials/{id}
     *
     * @param id 수정할 자재 ID (예: 7)
     * @param request 자재 수정 요청 정보 (자재명, 단가, 상태, 설명 등)
     * @return 수정된 자재의 ID
     */
    @Operation(summary = "자재 수정", description = "기존 자재 정보를 수정합니다.")
    @ApiErrorResponses.MaterialNotFound
    @PutMapping("/{id}")
    @RequirePermission(menu = "MM_MAT", authorities = {"AC_SYS", "AC_SAL", "AC_PRO"}, accessType = AccessType.WRITE)
    public ResponseEntity<Map<String, Integer>> updateMaterial(
            @Parameter(description = "자재 ID", example = "7")
            @PathVariable int id,

            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "자재 수정 요청 정보",
                    required = true
            )
            @RequestBody MaterialUpdateRequestDTO request) {

        materialCommandService.updateMaterial(id, request);
        return ResponseEntity.ok(Map.of("materialId", id));
    }

    /**
     * 자재 비활성화 처리
     *
     * PATCH /materials/{id}/deactivate
     *
     * @param id 비활성화할 자재 ID (예: 7)
     * @return 비활성화된 자재의 ID
     * @apiNote 실제 삭제가 아닌 비활성화 처리(soft delete)로, 데이터는 유지됩니다.
     */
    @Operation(
            summary = "자재 비활성화",
            description = "자재를 비활성화 처리합니다. (실삭제 아님)"
    )
    @PatchMapping("/{id}/deactivate")
    @RequirePermission(menu = "MM_MAT", authorities = {"AC_SYS", "AC_SAL", "AC_PRO"}, accessType = AccessType.WRITE)
    public ResponseEntity<Map<String, Integer>> deactivate(
            @Parameter(description = "자재 ID", example = "7")
            @PathVariable int id) {
        materialCommandService.deactivateMaterial(id);
        return ResponseEntity.ok(Map.of("materialId", id));
    }

    /**
     * 자재 활성화 처리
     *
     * PATCH /materials/{id}/activate
     *
     * @param id 활성화할 자재 ID (예: 7)
     * @return 활성화된 자재의 ID
     */
    @Operation(
            summary = "자재 활성화",
            description = "자재를 활성화 처리합니다."
    )
    @PatchMapping("/{id}/activate")
    @RequirePermission(menu = "MM_MAT", authorities = {"AC_SYS", "AC_SAL", "AC_PRO"}, accessType = AccessType.WRITE)
    public ResponseEntity<Map<String, Integer>> activate(
            @Parameter(description = "자재 ID", example = "7")
            @PathVariable int id) {
        materialCommandService.activateMaterial(id);
        return ResponseEntity.ok(Map.of("materialId", id));
    }
}
