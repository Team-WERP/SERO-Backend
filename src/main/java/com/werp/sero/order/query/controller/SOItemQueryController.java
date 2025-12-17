package com.werp.sero.order.query.controller;

import com.werp.sero.order.query.dto.SOItemResponseDTO;
import com.werp.sero.order.query.service.SOItemQueryService;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "주문 품목", description = "주문 품목 관련 API (Query)")
@RequestMapping("/orders")
@RequiredArgsConstructor
@RestController
public class SOItemQueryController {

    private final SOItemQueryService orderItemService;

    @Operation(summary = "주문 품목 목록 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "주문 목록 조회", content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(
                            schema = @Schema(implementation = SOItemResponseDTO.class)
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
    @GetMapping("/{orderId}/items")
    public ResponseEntity<List<SOItemResponseDTO>> findOrderList(@PathVariable("orderId") final int orderId) {

        final List<SOItemResponseDTO> response = orderItemService.findOrderItemsById(orderId);

        return ResponseEntity.ok(response);
    }
}
