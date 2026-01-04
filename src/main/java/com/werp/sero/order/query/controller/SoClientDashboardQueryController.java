package com.werp.sero.order.query.controller;

import com.werp.sero.employee.command.domain.aggregate.ClientEmployee;
import com.werp.sero.order.query.dto.SOClientDashboardResponseDTO;
import com.werp.sero.order.query.service.SOClientDashboardQueryService;
import com.werp.sero.security.annotation.CurrentUser;
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
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "고객사 대시보드", description = "고객사 대시보드 관련 API")
@RequestMapping("/clients/orders/dashboard")
@RequiredArgsConstructor
@RestController
public class SoClientDashboardQueryController {

    private final SOClientDashboardQueryService clientDashboardService;

    @GetMapping
    @Operation(summary = "고객사 대시보드 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "고객사 대시보드 조회", content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(
                            schema = @Schema(implementation = SOClientDashboardResponseDTO.class)
                    )
            ))
    })
    public ResponseEntity<SOClientDashboardResponseDTO> getDashboardData(
            @CurrentUser final ClientEmployee clientEmployee
    ) {
        int clientId = clientEmployee.getId();

        SOClientDashboardResponseDTO response = clientDashboardService.getDashboardData(clientId);

        return ResponseEntity.ok(response);
    }
}
