package com.werp.sero.order.command.application.controller;

import com.werp.sero.employee.command.domain.aggregate.ClientEmployee;
import com.werp.sero.order.command.application.dto.SOClientOrderDTO;
import com.werp.sero.order.command.application.dto.SODetailResponseDTO;
import com.werp.sero.order.command.application.service.SOClientCommandService;
import com.werp.sero.security.annotation.CurrentUser;
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

@Tag(name = "주문(고객사) - Command", description = "고객사 주문 관련 API")
@RequestMapping("/clients/orders")
@RequiredArgsConstructor
@RestController
public class SOClientCommandController {

    private final SOClientCommandService orderClientService;

    @Operation(summary = "고객사 주문 등록")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "고객사 주문 등록", content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(
                            schema = @Schema(implementation = SODetailResponseDTO.class)
                    )
            )),
            @ApiResponse(responseCode = "404", content = @Content(mediaType = "application/json", examples = {
                    @ExampleObject(name = "CLIENT_NOT_FOUND", value = """
                            {
                                "code": CLIENT001",
                                "message": "고객사 정보를 찾을 수 없습니다."
                            }
                            """)
            }))
    })
    @PostMapping
    public ResponseEntity<SOClientOrderDTO> createOrder(
            @CurrentUser final ClientEmployee clientEmployee,
            @Valid @RequestBody final SOClientOrderDTO request) {

        SOClientOrderDTO response = orderClientService.createOrder(clientEmployee,request);

        return ResponseEntity.ok(response);
    }
}
