package com.werp.sero.production.query.controller;

import com.werp.sero.production.query.dto.LineMaterialListItemDTO;
import com.werp.sero.production.query.dto.ProductionProcessResponseDTO;
import com.werp.sero.production.query.service.ProductionProcessQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(
        name = "Production Process (Query)",
        description = "생산 공정 관리 조회 API"
)
@RestController
@RequestMapping("/production-process")
@RequiredArgsConstructor
public class ProductionProcessQueryController {
    private final ProductionProcessQueryService productionProcessQueryService;

    @Operation(
            summary = "라인-품목(LineMaterial) 목록 조회",
            description = """
            생산 라인과 품목이 매핑된 목록을 조회합니다.
            """
    )
    @GetMapping("/line-material")
    public List<LineMaterialListItemDTO> lineMaterialList() {
        return productionProcessQueryService.getLineMaterialList();
    }

    @Operation(
            summary = "라인-품목(LineMaterial) 기준 공정 단계 조회",
            description = """
            선택한 라인-품목(LineMaterial)에 대한 생산 공정 단계 목록을 조회합니다.
            """
    )
    @GetMapping("/process")
    public List<ProductionProcessResponseDTO> processList(
            @RequestParam int lineMaterialId
    ) {
        return productionProcessQueryService.getProcessList(lineMaterialId);
    }
}
