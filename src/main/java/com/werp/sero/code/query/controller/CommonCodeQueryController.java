package com.werp.sero.code.query.controller;

import com.werp.sero.code.query.dto.CommonCodeDetailManageDTO;
import com.werp.sero.code.query.dto.CommonCodeTypeManageDTO;
import com.werp.sero.code.query.service.CommonCodeManageQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Common Code Query", description = "공통코드 조회 API")
@RestController
@RequestMapping("/common-codes")
@RequiredArgsConstructor
public class CommonCodeQueryController {

    private final CommonCodeManageQueryService commonCodeQueryService;

    @Operation(summary = "모든 코드 타입 조회", description = "공통코드 관리 페이지에서 좌측 코드 그룹 목록 조회")
    @GetMapping("/types")
    public ResponseEntity<List<CommonCodeTypeManageDTO>> getAllCodeTypes() {
        List<CommonCodeTypeManageDTO> codeTypes = commonCodeQueryService.getAllCodeTypes();
        return ResponseEntity.ok(codeTypes);
    }

    @Operation(summary = "특정 타입의 상세 코드 목록 조회", description = "선택한 코드 타입의 상세 코드 목록 조회")
    @GetMapping("/types/{typeCode}")
    public ResponseEntity<List<CommonCodeDetailManageDTO>> getCodeDetailsByType(
            @PathVariable String typeCode
    ) {
        List<CommonCodeDetailManageDTO> codeDetails = commonCodeQueryService.getCodeDetailsByType(typeCode);
        return ResponseEntity.ok(codeDetails);
    }
}
