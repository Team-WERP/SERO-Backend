package com.werp.sero.shipping.command.application.controller;

import com.werp.sero.employee.command.domain.aggregate.Employee;
import com.werp.sero.security.annotation.CurrentUser;
import com.werp.sero.shipping.command.application.dto.GICreateRequestDTO;
import com.werp.sero.shipping.command.application.service.GoodsIssueCommandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Tag(
        name = "출고지시 - Command",
        description = "출고지시 작성 API"
)
@RestController
@RequestMapping("/goods-issues")
@RequiredArgsConstructor
public class GoodsIssueCommandController {

    private final GoodsIssueCommandService goodsIssueCommandService;

    @Operation(
            summary = "출고지시 작성",
            description = """
                    납품서(Delivery Order)를 기준으로 출고지시를 작성합니다.

                    - 납품서 번호로 납품서 정보를 조회합니다
                    - 납품서의 모든 품목이 출고지시 품목으로 자동 포함됩니다
                    - 출고 창고를 선택해야 합니다
                    - 초기 상태는 GI_RVW(검토 중)입니다
                    """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "출고지시 작성 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Map.class),
                            examples = @ExampleObject(value = """
                                    {
                                        "message": "출고지시가 작성되었습니다.",
                                        "giCode": "GI-20251220-001"
                                    }
                                    """)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "재고 부족",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(name = "INSUFFICIENT_STOCK", value = """
                                    {
                                        "code": "WAREHOUSE005",
                                        "message": "재고가 부족합니다: 모터코어A(MC-A01): 필요 300개, 가용 100개"
                                    }
                                    """)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "납품서 또는 창고를 찾을 수 없음",
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(name = "DELIVERY_ORDER_NOT_FOUND", value = """
                                            {
                                                "code": "SHIPPING002",
                                                "message": "납품서 정보를 찾을 수 없습니다."
                                            }
                                            """),
                                    @ExampleObject(name = "WAREHOUSE_NOT_FOUND", value = """
                                            {
                                                "code": "WAREHOUSE002",
                                                "message": "창고 정보를 찾을 수 없습니다."
                                            }
                                            """)
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "중복 - 이미 출고지시가 생성된 납품서",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(name = "GOODS_ISSUE_ALREADY_EXISTS", value = """
                                    {
                                        "code": "SHIPPING003",
                                        "message": "해당 납품서로 이미 출고지시가 생성되었습니다."
                                    }
                                    """)
                    )
            )
    })
    @PostMapping
    public ResponseEntity<Map<String, String>> createGoodsIssue(
            @Valid @RequestBody GICreateRequestDTO requestDTO,
            @CurrentUser Employee currentEmployee
    ) {
        String giCode = goodsIssueCommandService.createGoodsIssue(requestDTO, currentEmployee);

        Map<String, String> response = new HashMap<>();
        response.put("message", "출고지시가 작성되었습니다.");
        response.put("giCode", giCode);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
