package com.werp.sero.shipping.query.controller;

import com.werp.sero.common.security.AccessType;
import com.werp.sero.common.security.RequirePermission;
import com.werp.sero.employee.command.domain.aggregate.Employee;
import com.werp.sero.security.annotation.CurrentUser;
import com.werp.sero.shipping.query.dto.DODetailResponseDTO;
import com.werp.sero.shipping.query.dto.DOListResponseDTO;
import com.werp.sero.shipping.query.service.DODetailQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/delivery-orders")
@RequiredArgsConstructor
@Tag(name = "DeliveryOrder", description = "납품서 관리 API")
public class DOQueryController {

    private final DODetailQueryService DODetailQueryService;

    @GetMapping
    @Operation(summary = "납품서 목록 조회", description = "납품서 목록을 조회합니다. 상태 필터링 가능합니다.")
    @RequirePermission(menu = "MM_DO", authorities = {"AC_SAL"}, accessType = AccessType.READ)
    public ResponseEntity<List<DOListResponseDTO>> getDeliveryOrdersByStatus(
            @Parameter(description = "납품서 상태 (선택사항)", example = "DO_BEFORE_GI")
            @RequestParam(required = false) String status,
            @CurrentUser Employee employee
    ) {
        List<DOListResponseDTO> response;
        if (status != null && !status.isEmpty()) {
            // 상태 필터가 있으면 담당자별로 필터링 (출고지시 작성용)
            response = DODetailQueryService.getDeliveryOrdersByStatusAndManager(status, employee.getId());
        } else {
            // 상태 필터 없으면 전체 조회 (납품서 관리 페이지용)
            response = DODetailQueryService.getAllDeliveryOrders();
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{doCode}")
    @Operation(summary = "납품서 상세 조회", description = "납품서 코드로 납품서 상세 정보를 조회합니다. (미리보기용)")
    public ResponseEntity<DODetailResponseDTO> getDeliveryOrderDetail(
            @Parameter(description = "납품서 코드", example = "DO-20251219-01")
            @PathVariable String doCode
    ) {
        DODetailResponseDTO response = DODetailQueryService.getDeliveryOrderDetail(doCode);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search/{orderId}")
    @Operation(summary = "납품서 목록 조회", description = "주문 코드로 납품서 목록을 조회합니다.")
    public ResponseEntity<List<DOListResponseDTO>> getDeliveryOrderListByOrderId(
            @Parameter(description = "주문 코드")
            @PathVariable int orderId
    ) {
        List<DOListResponseDTO> response = DODetailQueryService.getDeliveryOrderListByOrderId(orderId);
        return ResponseEntity.ok(response);
    }
}
