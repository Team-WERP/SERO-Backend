package com.werp.sero.order.command.application.controller;

import com.werp.sero.order.command.application.dto.SODetailResponseDTO;
import com.werp.sero.order.command.application.dto.SOGoalCreateRequestDTO;
import com.werp.sero.order.command.application.dto.SOGoalUpdateRequestDTO;
import com.werp.sero.order.command.application.dto.SOGoalResponseDTO;
import com.werp.sero.order.command.application.service.SODashboardCommandService;
import com.werp.sero.order.query.dto.SODashboardResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "주문(본사) 대시보드 - Command", description = "본사 주문 대시보드 관련 API")
@RequestMapping("/orders/dashboard")
@RequiredArgsConstructor
@RestController
public class SODashboardCommandController {

    private final SODashboardCommandService dashboardService;

    @Operation(summary = "주문 이달의 목표 수주 금액 설정")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "주문 이달의 목표 수주 금액 설정", content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(
                            schema = @Schema(implementation = SODashboardResponseDTO.SOGoalDTO.class)
                    )
            )),
            @ApiResponse(responseCode = "404", content = @Content(mediaType = "application/json", examples = {
                    @ExampleObject(name = "SALES_ORDER_MONTHLY_GOAL_NOT_FOUND", value = """
                            {
                                "code": "ORDER005",
                                "message": "이 달의 주문 목표를 찾을 수 없습니다."
                            }
                            """)
            }))
    })
    @PostMapping("/goal")
    public ResponseEntity<SOGoalResponseDTO> createMonthlyGoal(
            @RequestBody final SOGoalCreateRequestDTO request) {

        SOGoalResponseDTO response = dashboardService.createMonthlyGoal(request);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "주문 이달의 목표 수주 금액 수정")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "주문 이달의 목표 수주 금액 수정", content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(
                            schema = @Schema(implementation = SODashboardResponseDTO.SOGoalDTO.class)
                    )
            )),
            @ApiResponse(responseCode = "404", content = @Content(mediaType = "application/json", examples = {
                    @ExampleObject(name = "SALES_ORDER_MONTHLY_GOAL_NOT_FOUND", value = """
                            {
                                "code": "ORDER005",
                                "message": "이 달의 주문 목표를 찾을 수 없습니다."
                            }
                            """)
            }))
    })
    @PutMapping("/goal/{goalId}")
    public ResponseEntity<SOGoalResponseDTO> updateMonthlyGoalAmount(
            @PathVariable("goalId") final int goalId,
            @RequestBody final SOGoalUpdateRequestDTO request) {

        SOGoalResponseDTO response = dashboardService.updateMonthlyGoal(goalId, request.getGoalAmount());

        return ResponseEntity.ok(response);
    }
}
