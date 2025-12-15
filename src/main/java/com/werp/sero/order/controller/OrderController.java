package com.werp.sero.order.controller;

import com.werp.sero.order.dto.OrderCancelRequestDTO;
import com.werp.sero.order.dto.OrderDetailResponseDTO;
import com.werp.sero.order.dto.OrderManagerRequestDTO;
import com.werp.sero.order.dto.OrderResponseDTO;
import com.werp.sero.order.service.OrderService;
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

import java.util.List;

@Tag(name = "order", description = "주문 관련 API")
@RequestMapping("/orders")
@RequiredArgsConstructor
@RestController
public class OrderController {
    private final OrderService orderService;

    @Operation(summary = "주문 목록 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "주문 목록 조회", content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(
                            schema = @Schema(implementation = OrderResponseDTO.class)
                    )
            )),
            @ApiResponse(responseCode = "404", content = @Content(mediaType = "application/json", examples = {
                    @ExampleObject(name = "ORDER_LIST_NOT_FOUND", value = """
                            {
                                "code": "ORDER001",
                                "message": "Order list not found"
                            }
                            """)
            }))
    })
    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>> findOrderList() {

        final List<OrderResponseDTO> response = orderService.findOrderList();

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "주문 상세 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "주문 상세 조회", content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(
                            schema = @Schema(implementation = OrderDetailResponseDTO.class)
                    )
            )),
            @ApiResponse(responseCode = "404", content = @Content(mediaType = "application/json", examples = {
                    @ExampleObject(name = "ORDER_NOT_FOUND", value = """
                            {
                                "code": "ORDER002",
                                "message": "Order not found"
                            }
                            """)
            }))
    })
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDetailResponseDTO> findOrderDetails(@PathVariable("orderId") final int orderId) {

        final OrderDetailResponseDTO response = orderService.findOrderDetails(orderId);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "주문 담당자 배정")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "주문 담당자 배정", content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(
                            schema = @Schema(implementation = OrderDetailResponseDTO.class)
                    )
            )),
            @ApiResponse(responseCode = "404", content = @Content(mediaType = "application/json", examples = {
                    @ExampleObject(name = "EMPLOYEE_NOT_FOUND", value = """
                            {
                                "code": "EMPLOYEE002",
                                "message": "Employee not found"
                            }
                            """)
            }))
    })
    @PutMapping("/{orderId}/manager")
    public ResponseEntity<Void> assignOrderManager(
            @PathVariable("orderId") final int orderId,
            @RequestBody final OrderManagerRequestDTO request) {

        orderService.assignManagerToOrder(orderId, request.getEmpId());

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "주문 취소")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "주문 취소", content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(
                            schema = @Schema(implementation = OrderDetailResponseDTO.class)
                    )
            )),
            @ApiResponse(responseCode = "404", content = @Content(mediaType = "application/json", examples = {
                    @ExampleObject(name = "ORDER_NOT_FOUND", value = """
                            {
                                "code": "ORDER002",
                                "message": "Order not found"
                            }
                            """)
            }))
    })
    @PutMapping("/{orderId}/cancel")
    public ResponseEntity<OrderDetailResponseDTO> cancelOrder(
            @PathVariable("orderId") final int orderId,
            @Valid @RequestBody final OrderCancelRequestDTO request) {

        OrderDetailResponseDTO response = orderService.cancelOrder(orderId, request);

        return ResponseEntity.ok(response);
    }

}
