package com.werp.sero.material.service;

import com.werp.sero.employee.entity.Employee;
import com.werp.sero.material.dto.WarehouseStockDetailResponseDTO;
import com.werp.sero.material.dto.WarehouseStockListResponseDTO;
import com.werp.sero.material.entity.Material;
import com.werp.sero.material.entity.Warehouse;
import com.werp.sero.material.entity.WarehouseStock;
import com.werp.sero.material.entity.WarehouseStockHistory;
import com.werp.sero.material.exception.WarehouseStockNotFoundException;
import com.werp.sero.material.repository.WarehouseStockHistoryRepository;
import com.werp.sero.material.repository.WarehouseStockRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("WarehouseStockService 단위 테스트")
class WarehouseStockServiceImplTest {

    @Mock
    private WarehouseStockRepository warehouseStockRepository;

    @Mock
    private WarehouseStockHistoryRepository warehouseStockHistoryRepository;

    @InjectMocks
    private WarehouseStockServiceImpl warehouseStockService;

    private Employee testEmployee;
    private Warehouse warehouse1;
    private Warehouse warehouse2;
    private Material rawMaterial;
    private Material finishedGood;
    private WarehouseStock stock1;
    private WarehouseStock stock2;

    @BeforeEach
    void setUp() {
        testEmployee = mock(Employee.class);
        lenient().when(testEmployee.getId()).thenReturn(1);

        // 창고 설정
        warehouse1 = Warehouse.builder()
                .id(1)
                .name("제1창고")
                .address("서울시 강남구")
                .type("WHS_WH")
                .employee(testEmployee)
                .createdAt("2025-12-01 10:00:00")
                .build();

        warehouse2 = Warehouse.builder()
                .id(2)
                .name("제2창고")
                .address("서울시 강북구")
                .type("WHS_WH")
                .employee(testEmployee)
                .createdAt("2025-12-01 10:00:00")
                .build();

        // 자재 설정
        rawMaterial = Material.builder()
                .id(1)
                .name("원자재A")
                .materialCode("RAW-001")
                .spec("원자재 스펙")
                .baseUnit("kg")
                .type("MAT_RAW")
                .status("MAT_NORMAL")
                .unitPrice(1000L)
                .safetyStock(50)
                .employee(testEmployee)
                .bomList(new ArrayList<>())
                .build();

        finishedGood = Material.builder()
                .id(2)
                .name("완제품A")
                .materialCode("FIN-001")
                .spec("완제품 스펙")
                .baseUnit("EA")
                .type("MAT_FINISH")
                .status("MAT_NORMAL")
                .unitPrice(10000L)
                .safetyStock(20)
                .employee(testEmployee)
                .bomList(new ArrayList<>())
                .build();

        // 재고 설정
        stock1 = WarehouseStock.builder()
                .id(1)
                .warehouse(warehouse1)
                .material(rawMaterial)
                .safetyStock(50)
                .currentStock(100)
                .availableStock(100)
                .build();

        stock2 = WarehouseStock.builder()
                .id(2)
                .warehouse(warehouse2)
                .material(finishedGood)
                .safetyStock(20)
                .currentStock(15)  // 안전재고 미달
                .availableStock(15)
                .build();
    }

    @Test
    @DisplayName("재고 목록 조회 - 전체 조회")
    void getWarehouseStockList_All_Success() {
        // given
        List<WarehouseStock> stocks = List.of(stock1, stock2);
        given(warehouseStockRepository.findByCondition(null, null, null, null))
                .willReturn(stocks);

        // when
        List<WarehouseStockListResponseDTO> result = warehouseStockService.getWarehouseStockList(
                null, null, null, null
        );

        // then
        assertThat(result).hasSize(2);
        verify(warehouseStockRepository, times(1)).findByCondition(null, null, null, null);
    }

    @Test
    @DisplayName("재고 목록 조회 - 창고 ID로 필터링")
    void getWarehouseStockList_ByWarehouse_Success() {
        // given
        List<WarehouseStock> stocks = List.of(stock1);
        given(warehouseStockRepository.findByCondition(1, null, null, null))
                .willReturn(stocks);

        // when
        List<WarehouseStockListResponseDTO> result = warehouseStockService.getWarehouseStockList(
                1, null, null, null
        );

        // then
        assertThat(result).hasSize(1);
        verify(warehouseStockRepository, times(1)).findByCondition(1, null, null, null);
    }

    @Test
    @DisplayName("재고 목록 조회 - 자재 타입으로 필터링")
    void getWarehouseStockList_ByMaterialType_Success() {
        // given
        List<WarehouseStock> stocks = List.of(stock1);
        given(warehouseStockRepository.findByCondition(null, "MAT_RAW", null, null))
                .willReturn(stocks);

        // when
        List<WarehouseStockListResponseDTO> result = warehouseStockService.getWarehouseStockList(
                null, "MAT_RAW", null, null
        );

        // then
        assertThat(result).hasSize(1);
        verify(warehouseStockRepository, times(1)).findByCondition(null, "MAT_RAW", null, null);
    }

    @Test
    @DisplayName("재고 목록 조회 - 재고 상태로 필터링 (부족)")
    void getWarehouseStockList_ByStockStatus_Success() {
        // given
        List<WarehouseStock> stocks = List.of(stock2);
        given(warehouseStockRepository.findByCondition(null, null, "SHORTAGE", null))
                .willReturn(stocks);

        // when
        List<WarehouseStockListResponseDTO> result = warehouseStockService.getWarehouseStockList(
                null, null, "SHORTAGE", null
        );

        // then
        assertThat(result).hasSize(1);
        verify(warehouseStockRepository, times(1)).findByCondition(null, null, "SHORTAGE", null);
    }

    @Test
    @DisplayName("재고 목록 조회 - 키워드로 검색")
    void getWarehouseStockList_ByKeyword_Success() {
        // given
        List<WarehouseStock> stocks = List.of(stock1);
        given(warehouseStockRepository.findByCondition(null, null, null, "원자재"))
                .willReturn(stocks);

        // when
        List<WarehouseStockListResponseDTO> result = warehouseStockService.getWarehouseStockList(
                null, null, null, "원자재"
        );

        // then
        assertThat(result).hasSize(1);
        verify(warehouseStockRepository, times(1)).findByCondition(null, null, null, "원자재");
    }

    @Test
    @DisplayName("재고 목록 조회 - 복합 필터링")
    void getWarehouseStockList_MultipleFilters_Success() {
        // given
        List<WarehouseStock> stocks = List.of(stock1);
        given(warehouseStockRepository.findByCondition(1, "MAT_RAW", "NORMAL", "원자재"))
                .willReturn(stocks);

        // when
        List<WarehouseStockListResponseDTO> result = warehouseStockService.getWarehouseStockList(
                1, "MAT_RAW", "NORMAL", "원자재"
        );

        // then
        assertThat(result).hasSize(1);
        verify(warehouseStockRepository, times(1))
                .findByCondition(1, "MAT_RAW", "NORMAL", "원자재");
    }

    @Test
    @DisplayName("재고 상세 조회 - 성공")
    void getWarehouseStockDetail_Success() {
        // given
        WarehouseStockHistory history1 = WarehouseStockHistory.builder()
                .id(1)
                .warehouseStockId(1)
                .type("STOCK_IN")
                .reason("초기 입고")
                .changedQuantity(100)
                .currentStock(100)
                .createdAt("2025-12-01 10:00:00")
                .build();

        WarehouseStockHistory history2 = WarehouseStockHistory.builder()
                .id(2)
                .warehouseStockId(1)
                .type("STOCK_OUT")
                .reason("생산 출고")
                .changedQuantity(-50)
                .currentStock(50)
                .createdAt("2025-12-10 14:00:00")
                .build();

        List<WarehouseStockHistory> histories = List.of(history1, history2);

        given(warehouseStockRepository.findByIdWithDetails(1)).willReturn(Optional.of(stock1));
        given(warehouseStockHistoryRepository.findByWarehouseStockId(1)).willReturn(histories);

        // when
        WarehouseStockDetailResponseDTO result = warehouseStockService.getWarehouseStockDetail(1);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getCurrentStock()).isEqualTo(100);
        assertThat(result.getSafetyStock()).isEqualTo(50);
        assertThat(result.getStockHistory()).hasSize(2);
        assertThat(result.getStockHistory().get(0).getType()).isEqualTo("STOCK_IN");
        assertThat(result.getStockHistory().get(1).getType()).isEqualTo("STOCK_OUT");

        verify(warehouseStockRepository, times(1)).findByIdWithDetails(1);
        verify(warehouseStockHistoryRepository, times(1)).findByWarehouseStockId(1);
    }

    @Test
    @DisplayName("재고 상세 조회 - 존재하지 않는 재고")
    void getWarehouseStockDetail_NotFound() {
        // given
        given(warehouseStockRepository.findByIdWithDetails(999)).willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> warehouseStockService.getWarehouseStockDetail(999))
                .isInstanceOf(WarehouseStockNotFoundException.class);
        verify(warehouseStockRepository, times(1)).findByIdWithDetails(999);
        verify(warehouseStockHistoryRepository, never()).findByWarehouseStockId(anyInt());
    }

    @Test
    @DisplayName("재고 상세 조회 - 변동 이력이 없는 경우")
    void getWarehouseStockDetail_NoHistory() {
        // given
        given(warehouseStockRepository.findByIdWithDetails(1)).willReturn(Optional.of(stock1));
        given(warehouseStockHistoryRepository.findByWarehouseStockId(1)).willReturn(List.of());

        // when
        WarehouseStockDetailResponseDTO result = warehouseStockService.getWarehouseStockDetail(1);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getStockHistory()).isEmpty();
        verify(warehouseStockRepository, times(1)).findByIdWithDetails(1);
        verify(warehouseStockHistoryRepository, times(1)).findByWarehouseStockId(1);
    }

    @Test
    @DisplayName("재고 목록 조회 - 빈 목록")
    void getWarehouseStockList_EmptyResult() {
        // given
        given(warehouseStockRepository.findByCondition(null, null, null, "없는자재"))
                .willReturn(List.of());

        // when
        List<WarehouseStockListResponseDTO> result = warehouseStockService.getWarehouseStockList(
                null, null, null, "없는자재"
        );

        // then
        assertThat(result).isEmpty();
        verify(warehouseStockRepository, times(1)).findByCondition(null, null, null, "없는자재");
    }
}
