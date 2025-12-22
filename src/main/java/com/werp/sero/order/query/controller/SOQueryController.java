package com.werp.sero.order.query.controller;


import com.werp.sero.order.query.dto.SOFilterDTO;
import com.werp.sero.order.query.dto.SODetailsResponseDTO;
import com.werp.sero.order.query.dto.SOResponseDTO;
import com.werp.sero.order.query.service.SOQueryService;
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

@Tag(name = "주문(본사) - Query", description = "주문 관련 API")
@RequestMapping("/orders")
@RequiredArgsConstructor
@RestController
public class SOQueryController {
    private final SOQueryService orderService;

    @Operation(summary = "주문 목록 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "주문 목록 조회", content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(
                            schema = @Schema(implementation = SOResponseDTO.class)
                    )
            )),
            @ApiResponse(responseCode = "404", content = @Content(mediaType = "application/json", examples = {
                    @ExampleObject(name = "SALES_ORDER_LIST_NOT_FOUND", value = """
                            {
                                "code": "ORDER001",
                                "message": "주문 목록을 찾을 수 없습니다."
                            }
                            """)
            }))
    })
    @GetMapping
    public ResponseEntity<List<SOResponseDTO>> findOrderList(
            @ModelAttribute SOFilterDTO filter,
            @RequestParam(value = "page", required = false) Integer page){

        final List<SOResponseDTO> response = orderService.findOrderList(filter, page);

        return ResponseEntity.ok(response);
    }


    @Operation(summary = "주문 상세 정보 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "주문 상세 정보 조회", content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(
                            schema = @Schema(implementation = SODetailsResponseDTO.class)
                    )
            )),
            @ApiResponse(responseCode = "404", content = @Content(mediaType = "application/json", examples = {
                    @ExampleObject(name = "SALES_ORDER_NOT_FOUND", value = """
                            {
                                "code": "ORDER002",
                                "message": "주문을 찾을 수 없습니다."
                            }
                            """)
            }))
    })
    @GetMapping("/{orderId}")
    public ResponseEntity<SODetailsResponseDTO> findOrderDetails(
            @PathVariable("orderId") final int orderId) {

        final SODetailsResponseDTO response = orderService.findOrderDetailsById(orderId);

        return ResponseEntity.ok(response);
    }



}
