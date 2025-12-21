package com.werp.sero.shipping.command.application.controller;

import com.werp.sero.shipping.command.application.service.GoodsIssueCommandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Goods Issue Command", description = "출고지시 Command API")
@RestController
@RequestMapping("/goods-issues")
@RequiredArgsConstructor
public class GoodsIssueCommandController {

    private final GoodsIssueCommandService goodsIssueCommandService;

    @Operation(summary = "출고 완료 처리", description = "결재가 완료된 출고지시를 실행하여 실제 재고를 차감합니다.")
    @PostMapping("/{giCode}/complete")
    public ResponseEntity<Void> completeGoodsIssue(@PathVariable String giCode) {
        goodsIssueCommandService.completeGoodsIssue(giCode);
        return ResponseEntity.ok().build();
    }
}
