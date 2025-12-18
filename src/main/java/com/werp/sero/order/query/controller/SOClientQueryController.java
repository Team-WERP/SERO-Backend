package com.werp.sero.order.query.controller;

import com.werp.sero.employee.command.domain.aggregate.ClientEmployee;
import com.werp.sero.order.query.dto.SOClientResponseDTO;
import com.werp.sero.order.query.dto.SOResponseDTO;
import com.werp.sero.order.query.service.SOClientService;
import com.werp.sero.security.annotation.CurrentUser;
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

import java.util.List;

@Tag(name = "고객사 주문", description = "고객 주문 관련 API (Query)")
@RequestMapping("/clients/order")
@RequiredArgsConstructor
@RestController
public class SOClientQueryController {

    private final SOClientService soClientService;

    @Operation(summary = "고객사 주문 이력 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "고객사 주문 이력 조회", content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(
                            schema = @Schema(implementation = SOResponseDTO.class)
                    )
            )),
            @ApiResponse(responseCode = "404", content = @Content(mediaType = "application/json", examples = {
                    @ExampleObject(name = "CLIENT_CANNOT_FOUND", value = """
                            {
                                "code": "CLIENT001",
                                "message": "고객사 정보를 찾을 수 없습니다."
                            }
                            """)
            }))
    })
    @GetMapping("/history")
    public ResponseEntity<List<SOClientResponseDTO>> findOrderHistory(
                @CurrentUser final ClientEmployee clientEmployee ){

        final List<SOClientResponseDTO> response = soClientService.findOrderHistory(clientEmployee);

        return ResponseEntity.ok(response);
    }

}
