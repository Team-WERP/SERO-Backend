package com.werp.sero.material.controller;

import com.werp.sero.material.dto.WarehouseStockDetailResponseDTO;
import com.werp.sero.material.dto.WarehouseStockListResponseDTO;
import com.werp.sero.material.exception.WarehouseStockNotFoundException;
import com.werp.sero.material.service.WarehouseStockService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(WarehouseStockController.class)
@DisplayName("WarehouseStockController 통합 테스트")
class WarehouseStockControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private WarehouseStockService warehouseStockService;

    @Test
    @DisplayName("GET /api/warehouse-stocks - 전체 조회 성공")
    void getWarehouseStocks_Success() throws Exception {
        // given
        List<WarehouseStockListResponseDTO> stocks = List.of(
                WarehouseStockListResponseDTO.builder()
                        .id(1)
                        .warehouseName("제1창고")
                        .materialName("원자재A")
                        .materialCode("RAW-001")
                        .currentStock(100)
                        .safetyStock(50)
                        .stockStatus("NORMAL")
                        .build(),
                WarehouseStockListResponseDTO.builder()
                        .id(2)
                        .warehouseName("제2창고")
                        .materialName("완제품A")
                        .materialCode("FIN-001")
                        .currentStock(10)
                        .safetyStock(20)
                        .stockStatus("LOW")
                        .build()
        );
        given(warehouseStockService.getWarehouseStockList(null, null, null, null))
                .willReturn(stocks);

        // when & then
        mockMvc.perform(get("/api/warehouse-stocks"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].warehouseName").value("제1창고"))
                .andExpect(jsonPath("$[0].stockStatus").value("NORMAL"))
                .andExpect(jsonPath("$[1].stockStatus").value("LOW"));

        verify(warehouseStockService, times(1))
                .getWarehouseStockList(null, null, null, null);
    }

    @Test
    @DisplayName("GET /api/warehouse-stocks - 창고 ID로 필터링")
    void getWarehouseStocks_WithWarehouseId() throws Exception {
        // given
        List<WarehouseStockListResponseDTO> stocks = List.of(
                WarehouseStockListResponseDTO.builder()
                        .id(1)
                        .warehouseName("제1창고")
                        .materialName("원자재A")
                        .materialCode("RAW-001")
                        .currentStock(100)
                        .safetyStock(50)
                        .stockStatus("NORMAL")
                        .build()
        );
        given(warehouseStockService.getWarehouseStockList(1, null, null, null))
                .willReturn(stocks);

        // when & then
        mockMvc.perform(get("/api/warehouse-stocks")
                        .param("warehouseId", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].warehouseName").value("제1창고"));

        verify(warehouseStockService, times(1))
                .getWarehouseStockList(1, null, null, null);
    }

    @Test
    @DisplayName("GET /api/warehouse-stocks - 자재 타입으로 필터링")
    void getWarehouseStocks_WithMaterialType() throws Exception {
        // given
        List<WarehouseStockListResponseDTO> stocks = List.of(
                WarehouseStockListResponseDTO.builder()
                        .id(1)
                        .warehouseName("제1창고")
                        .materialName("원자재A")
                        .materialCode("RAW-001")
                        .materialType("MAT_RAW")
                        .currentStock(100)
                        .safetyStock(50)
                        .stockStatus("NORMAL")
                        .build()
        );
        given(warehouseStockService.getWarehouseStockList(null, "MAT_RAW", null, null))
                .willReturn(stocks);

        // when & then
        mockMvc.perform(get("/api/warehouse-stocks")
                        .param("materialType", "MAT_RAW"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].materialType").value("MAT_RAW"));

        verify(warehouseStockService, times(1))
                .getWarehouseStockList(null, "MAT_RAW", null, null);
    }

    @Test
    @DisplayName("GET /api/warehouse-stocks - 재고 상태로 필터링")
    void getWarehouseStocks_WithStockStatus() throws Exception {
        // given
        List<WarehouseStockListResponseDTO> stocks = List.of(
                WarehouseStockListResponseDTO.builder()
                        .id(2)
                        .warehouseName("제2창고")
                        .materialName("완제품A")
                        .materialCode("FIN-001")
                        .currentStock(10)
                        .safetyStock(20)
                        .stockStatus("LOW")
                        .build()
        );
        given(warehouseStockService.getWarehouseStockList(null, null, "LOW", null))
                .willReturn(stocks);

        // when & then
        mockMvc.perform(get("/api/warehouse-stocks")
                        .param("stockStatus", "LOW"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].stockStatus").value("LOW"));

        verify(warehouseStockService, times(1))
                .getWarehouseStockList(null, null, "LOW", null);
    }

    @Test
    @DisplayName("GET /api/warehouse-stocks - 키워드로 검색")
    void getWarehouseStocks_WithKeyword() throws Exception {
        // given
        List<WarehouseStockListResponseDTO> stocks = List.of(
                WarehouseStockListResponseDTO.builder()
                        .id(1)
                        .warehouseName("제1창고")
                        .materialName("원자재A")
                        .materialCode("RAW-001")
                        .currentStock(100)
                        .safetyStock(50)
                        .stockStatus("NORMAL")
                        .build()
        );
        given(warehouseStockService.getWarehouseStockList(null, null, null, "원자재"))
                .willReturn(stocks);

        // when & then
        mockMvc.perform(get("/api/warehouse-stocks")
                        .param("keyword", "원자재"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].materialName").value("원자재A"));

        verify(warehouseStockService, times(1))
                .getWarehouseStockList(null, null, null, "원자재");
    }

    @Test
    @DisplayName("GET /api/warehouse-stocks/{id} - 상세 조회 성공")
    void getWarehouseStock_Success() throws Exception {
        // given
        WarehouseStockDetailResponseDTO stock = WarehouseStockDetailResponseDTO.builder()
                .id(1)
                .currentStock(100)
                .safetyStock(50)
                .availableStock(100)
                .stockStatus("NORMAL")
                .build();
        given(warehouseStockService.getWarehouseStockDetail(1)).willReturn(stock);

        // when & then
        mockMvc.perform(get("/api/warehouse-stocks/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.currentStock").value(100))
                .andExpect(jsonPath("$.safetyStock").value(50))
                .andExpect(jsonPath("$.stockStatus").value("NORMAL"));

        verify(warehouseStockService, times(1)).getWarehouseStockDetail(1);
    }

    @Test
    @DisplayName("GET /api/warehouse-stocks/{id} - 존재하지 않는 재고")
    void getWarehouseStock_NotFound() throws Exception {
        // given
        given(warehouseStockService.getWarehouseStockDetail(999))
                .willThrow(new WarehouseStockNotFoundException("재고를 찾을 수 없습니다."));

        // when & then
        mockMvc.perform(get("/api/warehouse-stocks/999"))
                .andDo(print())
                .andExpect(status().is4xxClientError());

        verify(warehouseStockService, times(1)).getWarehouseStockDetail(999);
    }

    @Test
    @DisplayName("GET /api/warehouse-stocks - 복합 필터링")
    void getWarehouseStocks_MultipleFilters() throws Exception {
        // given
        List<WarehouseStockListResponseDTO> stocks = List.of(
                WarehouseStockListResponseDTO.builder()
                        .id(1)
                        .warehouseName("제1창고")
                        .materialName("원자재A")
                        .materialCode("RAW-001")
                        .materialType("MAT_RAW")
                        .currentStock(100)
                        .safetyStock(50)
                        .stockStatus("NORMAL")
                        .build()
        );
        given(warehouseStockService.getWarehouseStockList(1, "MAT_RAW", "NORMAL", "원자재"))
                .willReturn(stocks);

        // when & then
        mockMvc.perform(get("/api/warehouse-stocks")
                        .param("warehouseId", "1")
                        .param("materialType", "MAT_RAW")
                        .param("stockStatus", "NORMAL")
                        .param("keyword", "원자재"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].warehouseName").value("제1창고"))
                .andExpect(jsonPath("$[0].materialType").value("MAT_RAW"))
                .andExpect(jsonPath("$[0].stockStatus").value("NORMAL"));

        verify(warehouseStockService, times(1))
                .getWarehouseStockList(1, "MAT_RAW", "NORMAL", "원자재");
    }
}
