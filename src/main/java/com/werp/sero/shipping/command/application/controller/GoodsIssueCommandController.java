package com.werp.sero.shipping.command.application.controller;

import com.werp.sero.employee.command.domain.aggregate.Employee;
import com.werp.sero.security.annotation.CurrentUser;
import com.werp.sero.shipping.command.application.dto.GIAssignManagerResponseDTO;
import com.werp.sero.shipping.command.application.dto.GICompleteResponseDTO;
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
import org.springframework.web.bind.annotation.*;

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
            summary = "출고 완료 처리",
            description = "결재가 완료된 출고지시를 실행하여 실제 재고를 차감합니다."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "출고 처리 완료",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = GICompleteResponseDTO.class),
                            examples = @ExampleObject(value = """
                                    {
                                        "giCode": "GI-20251220-001",
                                        "warehouseName": "중앙창고",
                                        "completedAt": "2025-12-20 14:30",
                                        "trackingNumber": "SERO-20251222-D001",
                                        "driverName": "김기사",
                                        "driverContact": "010-1234-5678",
                                        "items": [
                                            {
                                                "itemCode": "MC-A01",
                                                "itemName": "모터코어A",
                                                "spec": "100x200",
                                                "quantity": 100,
                                                "unit": "EA",
                                                "remainingStock": 400
                                            },
                                            {
                                                "itemCode": "MC-B01",
                                                "itemName": "모터코어B",
                                                "spec": "150x250",
                                                "quantity": 50,
                                                "unit": "EA",
                                                "remainingStock": 200
                                            }
                                        ]
                                    }
                                    """)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "출고 처리 불가 상태",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(name = "INVALID_GOODS_ISSUE_STATUS", value = """
                                    {
                                        "code": "SHIPPING004",
                                        "message": "출고 처리할 수 없는 상태입니다."
                                    }
                                    """)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "출고지시를 찾을 수 없음",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(name = "GOODS_ISSUE_NOT_FOUND", value = """
                                    {
                                        "code": "SHIPPING001",
                                        "message": "출고지시 정보를 찾을 수 없습니다."
                                    }
                                    """)
                    )
            )
    })
    @PostMapping("/{giCode}/complete")
    public ResponseEntity<GICompleteResponseDTO> completeGoodsIssue(@PathVariable String giCode) {
        GICompleteResponseDTO response = goodsIssueCommandService.completeGoodsIssue(giCode);
        return ResponseEntity.ok(response);
    }

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

    @Operation(
            summary = "출고지시 담당자 배정",
            description = "물류팀 직원이 출고지시서의 담당자로 자신을 배정합니다."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "담당자 배정 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = GIAssignManagerResponseDTO.class),
                            examples = @ExampleObject(value = """
                                    {
                                        "giCode": "GI-20251220-001",
                                        "managerId": 5,
                                        "managerName": "김물류",
                                        "managerDepartment": "물류팀",
                                        "assignedAt": "2025-12-20 14:30"
                                    }
                                    """)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "출고지시서를 찾을 수 없음",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(name = "GOODS_ISSUE_NOT_FOUND", value = """
                                    {
                                        "code": "SHIPPING001",
                                        "message": "출고지시 정보를 찾을 수 없습니다."
                                    }
                                    """)
                    )
            )
    })
    @PatchMapping("/{giCode}/assign-manager")
    public ResponseEntity<GIAssignManagerResponseDTO> assignManager(
            @PathVariable String giCode,
            @CurrentUser Employee currentEmployee
    ) {
        GIAssignManagerResponseDTO response = goodsIssueCommandService.assignManager(giCode, currentEmployee);
        return ResponseEntity.ok(response);
    }
}
