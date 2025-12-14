package com.werp.sero.material.service;

import com.werp.sero.employee.entity.Employee;
import com.werp.sero.material.dto.BomExplosionRequestDTO;
import com.werp.sero.material.dto.BomExplosionResponseDTO;
import com.werp.sero.material.dto.BomImplosionResponseDTO;
import com.werp.sero.material.dto.MaterialSearchResponseDTO;
import com.werp.sero.material.entity.Bom;
import com.werp.sero.material.entity.Material;
import com.werp.sero.material.exception.MaterialNotFoundException;
import com.werp.sero.material.repository.BomRepository;
import com.werp.sero.material.repository.MaterialRepository;
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
@DisplayName("BomCalculationService 단위 테스트")
class BomCalculationServiceImplTest {

    @Mock
    private MaterialRepository materialRepository;

    @Mock
    private BomRepository bomRepository;

    @InjectMocks
    private BomCalculationServiceImpl bomCalculationService;

    private Employee testEmployee;
    private Material finishedGood;
    private Material rawMaterialA;
    private Material rawMaterialB;
    private Bom bom1;
    private Bom bom2;

    @BeforeEach
    void setUp() {
        testEmployee = mock(Employee.class);
        lenient().when(testEmployee.getId()).thenReturn(1);

        // 원자재 A
        rawMaterialA = Material.builder()
                .id(2)
                .name("원자재A")
                .materialCode("RAW-001")
                .spec("원자재A 스펙")
                .baseUnit("kg")
                .type("MAT_RAW")
                .status("MAT_NORMAL")
                .unitPrice(1000L)
                .employee(testEmployee)
                .bomList(new ArrayList<>())
                .build();

        // 원자재 B
        rawMaterialB = Material.builder()
                .id(3)
                .name("원자재B")
                .materialCode("RAW-002")
                .spec("원자재B 스펙")
                .baseUnit("L")
                .type("MAT_RAW")
                .status("MAT_NORMAL")
                .unitPrice(2000L)
                .employee(testEmployee)
                .bomList(new ArrayList<>())
                .build();

        // 완제품
        finishedGood = Material.builder()
                .id(1)
                .name("완제품A")
                .materialCode("FIN-001")
                .spec("완제품 스펙")
                .baseUnit("EA")
                .type("MAT_FINISH")
                .status("MAT_NORMAL")
                .unitPrice(10000L)
                .employee(testEmployee)
                .bomList(new ArrayList<>())
                .build();

        // BOM 1: 완제품 1개당 원자재A 5kg 필요
        bom1 = Bom.builder()
                .id(1)
                .material(finishedGood)
                .rawMaterial(rawMaterialA)
                .requirement(5)
                .note("BOM 메모1")
                .createdAt("2025-12-14 10:00:00")
                .build();

        // BOM 2: 완제품 1개당 원자재B 3L 필요
        bom2 = Bom.builder()
                .id(2)
                .material(finishedGood)
                .rawMaterial(rawMaterialB)
                .requirement(3)
                .note("BOM 메모2")
                .createdAt("2025-12-14 10:00:00")
                .build();
    }

    @Test
    @DisplayName("자재 검색 - 키워드로 검색 성공")
    void searchMaterials_ByKeyword_Success() {
        // given
        List<Material> materials = List.of(finishedGood, rawMaterialA);
        given(materialRepository.findByCondition(null, null, "자재")).willReturn(materials);

        // when
        List<MaterialSearchResponseDTO> result = bomCalculationService.searchMaterials("자재", null);

        // then
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getName()).isEqualTo("완제품A");
        assertThat(result.get(1).getName()).isEqualTo("원자재A");
        verify(materialRepository, times(1)).findByCondition(null, null, "자재");
    }

    @Test
    @DisplayName("자재 검색 - 타입 필터링")
    void searchMaterials_ByType_Success() {
        // given
        List<Material> materials = List.of(rawMaterialA, rawMaterialB);
        given(materialRepository.findByCondition("MAT_RAW", null, null)).willReturn(materials);

        // when
        List<MaterialSearchResponseDTO> result = bomCalculationService.searchMaterials(null, "MAT_RAW");

        // then
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getType()).isEqualTo("MAT_RAW");
        assertThat(result.get(1).getType()).isEqualTo("MAT_RAW");
        verify(materialRepository, times(1)).findByCondition("MAT_RAW", null, null);
    }

    @Test
    @DisplayName("자재 검색 - 타입과 키워드로 검색")
    void searchMaterials_ByTypeAndKeyword_Success() {
        // given
        List<Material> materials = List.of(rawMaterialA);
        given(materialRepository.findByCondition("MAT_RAW", null, "A")).willReturn(materials);

        // when
        List<MaterialSearchResponseDTO> result = bomCalculationService.searchMaterials("A", "MAT_RAW");

        // then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("원자재A");
        verify(materialRepository, times(1)).findByCondition("MAT_RAW", null, "A");
    }

    @Test
    @DisplayName("BOM 정전개 - 성공")
    void calculateExplosion_Success() {
        // given
        BomExplosionRequestDTO request = new BomExplosionRequestDTO(1, 10); // 완제품 10개 생산
        List<Bom> bomList = List.of(bom1, bom2);

        given(materialRepository.findById(1)).willReturn(Optional.of(finishedGood));
        given(bomRepository.findByMaterialIdWithRawMaterial(1)).willReturn(bomList);

        // when
        BomExplosionResponseDTO result = bomCalculationService.calculateExplosion(request);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getFinishedGood().getName()).isEqualTo("완제품A");
        assertThat(result.getRequestedQuantity()).isEqualTo(10);
        assertThat(result.getTotalMaterialCount()).isEqualTo(2);

        // 소요량 검증
        List<BomExplosionResponseDTO.RequiredMaterial> requiredMaterials = result.getRequiredMaterials();
        assertThat(requiredMaterials).hasSize(2);

        // 원자재A: 단위소요량 5 * 생산수량 10 = 총 50kg
        BomExplosionResponseDTO.RequiredMaterial reqA = requiredMaterials.get(0);
        assertThat(reqA.getMaterialName()).isEqualTo("원자재A");
        assertThat(reqA.getUnitRequirement()).isEqualTo(5);
        assertThat(reqA.getTotalRequirement()).isEqualTo(50);
        assertThat(reqA.getUnitPrice()).isEqualTo(1000L);
        assertThat(reqA.getTotalPrice()).isEqualTo(50000L); // 1000 * 50

        // 원자재B: 단위소요량 3 * 생산수량 10 = 총 30L
        BomExplosionResponseDTO.RequiredMaterial reqB = requiredMaterials.get(1);
        assertThat(reqB.getMaterialName()).isEqualTo("원자재B");
        assertThat(reqB.getUnitRequirement()).isEqualTo(3);
        assertThat(reqB.getTotalRequirement()).isEqualTo(30);
        assertThat(reqB.getUnitPrice()).isEqualTo(2000L);
        assertThat(reqB.getTotalPrice()).isEqualTo(60000L); // 2000 * 30

        verify(materialRepository, times(1)).findById(1);
        verify(bomRepository, times(1)).findByMaterialIdWithRawMaterial(1);
    }

    @Test
    @DisplayName("BOM 정전개 - 완제품이 존재하지 않음")
    void calculateExplosion_MaterialNotFound() {
        // given
        BomExplosionRequestDTO request = new BomExplosionRequestDTO(999, 10);
        given(materialRepository.findById(999)).willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> bomCalculationService.calculateExplosion(request))
                .isInstanceOf(MaterialNotFoundException.class);
        verify(materialRepository, times(1)).findById(999);
        verify(bomRepository, never()).findByMaterialIdWithRawMaterial(anyInt());
    }

    @Test
    @DisplayName("BOM 정전개 - BOM이 없는 경우")
    void calculateExplosion_NoBom() {
        // given
        BomExplosionRequestDTO request = new BomExplosionRequestDTO(1, 10);
        given(materialRepository.findById(1)).willReturn(Optional.of(finishedGood));
        given(bomRepository.findByMaterialIdWithRawMaterial(1)).willReturn(List.of());

        // when
        BomExplosionResponseDTO result = bomCalculationService.calculateExplosion(request);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getRequiredMaterials()).isEmpty();
        assertThat(result.getTotalMaterialCount()).isEqualTo(0);
        verify(materialRepository, times(1)).findById(1);
        verify(bomRepository, times(1)).findByMaterialIdWithRawMaterial(1);
    }

    @Test
    @DisplayName("BOM 역전개 - 성공")
    void calculateImplosion_Success() {
        // given
        List<Bom> bomList = List.of(bom1); // 원자재A를 사용하는 완제품 목록

        given(materialRepository.findById(2)).willReturn(Optional.of(rawMaterialA));
        given(bomRepository.findByRawMaterialIdWithMaterial(2)).willReturn(bomList);

        // when
        BomImplosionResponseDTO result = bomCalculationService.calculateImplosion(2);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getRawMaterial().getName()).isEqualTo("원자재A");
        assertThat(result.getRawMaterial().getMaterialCode()).isEqualTo("RAW-001");
        assertThat(result.getTotalProductCount()).isEqualTo(1);

        // 사용처 완제품 검증
        List<BomImplosionResponseDTO.UsedInProduct> usedInProducts = result.getUsedInProducts();
        assertThat(usedInProducts).hasSize(1);

        BomImplosionResponseDTO.UsedInProduct product = usedInProducts.get(0);
        assertThat(product.getProductName()).isEqualTo("완제품A");
        assertThat(product.getProductCode()).isEqualTo("FIN-001");
        assertThat(product.getRequirement()).isEqualTo(5);
        assertThat(product.getProductUnitPrice()).isEqualTo(10000L);

        verify(materialRepository, times(1)).findById(2);
        verify(bomRepository, times(1)).findByRawMaterialIdWithMaterial(2);
    }

    @Test
    @DisplayName("BOM 역전개 - 원자재가 존재하지 않음")
    void calculateImplosion_MaterialNotFound() {
        // given
        given(materialRepository.findById(999)).willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> bomCalculationService.calculateImplosion(999))
                .isInstanceOf(MaterialNotFoundException.class);
        verify(materialRepository, times(1)).findById(999);
        verify(bomRepository, never()).findByRawMaterialIdWithMaterial(anyInt());
    }

    @Test
    @DisplayName("BOM 역전개 - 사용처가 없는 경우")
    void calculateImplosion_NoUsage() {
        // given
        given(materialRepository.findById(2)).willReturn(Optional.of(rawMaterialA));
        given(bomRepository.findByRawMaterialIdWithMaterial(2)).willReturn(List.of());

        // when
        BomImplosionResponseDTO result = bomCalculationService.calculateImplosion(2);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getUsedInProducts()).isEmpty();
        assertThat(result.getTotalProductCount()).isEqualTo(0);
        verify(materialRepository, times(1)).findById(2);
        verify(bomRepository, times(1)).findByRawMaterialIdWithMaterial(2);
    }

    @Test
    @DisplayName("BOM 정전개 - 대량 생산 수량")
    void calculateExplosion_LargeQuantity() {
        // given
        BomExplosionRequestDTO request = new BomExplosionRequestDTO(1, 1000);
        List<Bom> bomList = List.of(bom1, bom2);

        given(materialRepository.findById(1)).willReturn(Optional.of(finishedGood));
        given(bomRepository.findByMaterialIdWithRawMaterial(1)).willReturn(bomList);

        // when
        BomExplosionResponseDTO result = bomCalculationService.calculateExplosion(request);

        // then
        assertThat(result.getRequestedQuantity()).isEqualTo(1000);

        // 원자재A: 5 * 1000 = 5000kg
        BomExplosionResponseDTO.RequiredMaterial reqA = result.getRequiredMaterials().get(0);
        assertThat(reqA.getTotalRequirement()).isEqualTo(5000);
        assertThat(reqA.getTotalPrice()).isEqualTo(5000000L); // 1000 * 5000

        // 원자재B: 3 * 1000 = 3000L
        BomExplosionResponseDTO.RequiredMaterial reqB = result.getRequiredMaterials().get(1);
        assertThat(reqB.getTotalRequirement()).isEqualTo(3000);
        assertThat(reqB.getTotalPrice()).isEqualTo(6000000L); // 2000 * 3000
    }
}
