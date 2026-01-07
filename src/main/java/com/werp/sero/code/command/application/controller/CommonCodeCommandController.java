package com.werp.sero.code.command.application.controller;

import com.werp.sero.code.command.application.dto.CommonCodeCreateRequestDTO;
import com.werp.sero.code.command.application.dto.CommonCodeResponseDTO;
import com.werp.sero.code.command.application.dto.CommonCodeUpdateRequestDTO;
import com.werp.sero.code.command.application.service.CommonCodeCommandService;
import com.werp.sero.common.security.AccessType;
import com.werp.sero.common.security.RequirePermission;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Common Code Command", description = "공통코드 관리 API (관리자 전용)")
@RestController
@RequestMapping("/codes")
@RequiredArgsConstructor
public class CommonCodeCommandController {

    private final CommonCodeCommandService commonCodeCommandService;

    @Operation(summary = "공통코드 생성", description = "새로운 공통코드를 생성합니다. (관리자 전용)")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "공통코드 생성 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "403", description = "권한 없음"),
            @ApiResponse(responseCode = "404", description = "공통코드 타입을 찾을 수 없음"),
            @ApiResponse(responseCode = "409", description = "이미 존재하는 코드")
    })
    @PostMapping
    @RequirePermission(menu = "MM_CODE", authorities = {"AC_SYS"}, accessType = AccessType.WRITE)
    public ResponseEntity<CommonCodeResponseDTO> createCode(
            @Valid @RequestBody CommonCodeCreateRequestDTO request
    ) {
        CommonCodeResponseDTO response = commonCodeCommandService.createCode(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "공통코드 수정", description = "기존 공통코드의 정보를 수정합니다. (관리자 전용)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "공통코드 수정 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "403", description = "권한 없음"),
            @ApiResponse(responseCode = "404", description = "공통코드를 찾을 수 없음")
    })
    @PutMapping("/{code}")
    @RequirePermission(menu = "MM_CODE", authorities = {"AC_SYS"}, accessType = AccessType.WRITE)
    public ResponseEntity<CommonCodeResponseDTO> updateCode(
            @PathVariable String code,
            @Valid @RequestBody CommonCodeUpdateRequestDTO request
    ) {
        CommonCodeResponseDTO response = commonCodeCommandService.updateCode(code, request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "공통코드 삭제", description = "공통코드를 삭제합니다. (관리자 전용)")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "공통코드 삭제 성공"),
            @ApiResponse(responseCode = "403", description = "권한 없음"),
            @ApiResponse(responseCode = "404", description = "공통코드를 찾을 수 없음")
    })
    @DeleteMapping("/{code}")
    @RequirePermission(menu = "MM_CODE", authorities = {"AC_SYS"}, accessType = AccessType.WRITE)
    public ResponseEntity<Void> deleteCode(@PathVariable String code) {
        commonCodeCommandService.deleteCode(code);
        return ResponseEntity.noContent().build();
    }
}
