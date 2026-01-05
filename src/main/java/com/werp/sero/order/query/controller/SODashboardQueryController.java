package com.werp.sero.order.query.controller;

import com.werp.sero.order.command.application.dto.SOGoalResponseDTO;
import com.werp.sero.order.query.dto.SOCalendarDTO;
import com.werp.sero.order.query.dto.SODashboardResponseDTO;
import com.werp.sero.order.query.service.SODashboardQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "주문 대시보드", description = "주문 대시보드 관련 API")
@RequestMapping("/orders/dashboard")
@RequiredArgsConstructor
@RestController
public class SODashboardQueryController {

    private final SODashboardQueryService dashboardService;

    @Operation(summary = "대시보드 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "대시보드 조회", content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(
                            schema = @Schema(implementation = SODashboardResponseDTO.class)
                    )
            ))
    })
    @GetMapping
    public ResponseEntity<SODashboardResponseDTO> getDashboard(@RequestParam int year, @RequestParam int month) {

        SODashboardResponseDTO response = dashboardService.getDashboardData(year,month);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "대시보드 캘린더 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "대시보드 캘린더 조회 성공", content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = SOCalendarDTO.class))
            ))
    })
    @GetMapping("/calendar")
    public ResponseEntity<List<SOCalendarDTO>> getDashboardCalendar(@RequestParam int year, @RequestParam int month) {

        List<SOCalendarDTO> response = dashboardService.getDashboardCalendar(year, month);

        return ResponseEntity.ok(response);
    }



}
