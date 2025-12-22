package com.werp.sero.production.command.application.controller;

import com.werp.sero.employee.command.domain.aggregate.Employee;
import com.werp.sero.production.command.application.dto.wo.WorkOrderCreateRequestDTO;
import com.werp.sero.production.command.application.service.WOCommandService;
import com.werp.sero.security.annotation.CurrentUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/work-orders")
@RequiredArgsConstructor
@Tag(name = "작업지시 - Command")
public class WOCommandController {
    private final WOCommandService woCommandService;

    @Operation(
            summary = "생산계획 기반 작업지시 생성",
            description = """
                확정된 생산계획(PP)을 기준으로
                기간(start_date ~ end_date) 내 작업지시를 일자 단위로 자동 생성합니다.
                
                제약:
                - PP 상태는 PP_CONFIRMED 이어야 함
                - 동일 PP에 대해 중복 생성 불가
                """
    )
    @PostMapping
    public ResponseEntity<Void> createWorkOrders(
            @RequestBody WorkOrderCreateRequestDTO request,
            @CurrentUser Employee currentEmployee
    ) {
        woCommandService.createFromProductionPlan(
                request.getPpId(),
                currentEmployee
        );
        return ResponseEntity.ok().build();
    }
}
