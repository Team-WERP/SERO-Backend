package com.werp.sero.order.command.application.controller;

import com.werp.sero.order.command.application.dto.*;
import com.werp.sero.order.command.application.service.SOCommandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "주문(본사) - Command", description = "본사 주문 관련 API")
@RequestMapping("/orders")
@RequiredArgsConstructor
@RestController
public class SOCommandController {
    private final SOCommandService orderService;

    @Operation(summary = "주문 취소")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "주문 취소", content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(
                            schema = @Schema(implementation = SODetailResponseDTO.class)
                    )
            )),
            @ApiResponse(responseCode = "404", content = @Content(mediaType = "application/json", examples = {
                    @ExampleObject(name = "ORDER_NOT_FOUND", value = """
                            {
                                "code": "ORDER002",
                                "message": "주문을 찾을 수 없습니다."
                            }
                            """)
            }))
    })
    @PutMapping("/{orderId}/cancel")
    public ResponseEntity<SODetailResponseDTO> cancelOrder(
            @PathVariable("orderId") final int orderId,
            @Valid @RequestBody final SOCancelRequestDTO request) {

        SODetailResponseDTO response = orderService.cancelOrder(orderId, request);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "주문 담당자 배정")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "주문 담당자 배정", content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(
                            schema = @Schema(implementation = SODetailResponseDTO.class)
                    )
            )),
            @ApiResponse(responseCode = "404", content = @Content(mediaType = "application/json", examples = {
                    @ExampleObject(name = "ORDER_NOT_FOUND", value = """
                            {
                                "code": "ORDER002",
                                "message": "주문을 찾을 수 없습니다."
                            }
                            """)
            }))
    })
    @PutMapping("/{orderId}/manager")
    public ResponseEntity<SODetailResponseDTO> assignOrderManager(
            @PathVariable("orderId") final int orderId,
            @Valid @RequestBody final SOManagerRequestDTO request) {

        SODetailResponseDTO response = orderService.assignManager(orderId, request.getEmpId());

        return ResponseEntity.ok(response);
    }
}
