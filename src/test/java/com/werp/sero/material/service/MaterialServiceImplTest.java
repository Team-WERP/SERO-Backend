package com.werp.sero.material.service;

import com.werp.sero.employee.entity.Employee;
import com.werp.sero.employee.repository.EmployeeRepository;
import com.werp.sero.material.dto.MaterialCreateRequestDTO;
import com.werp.sero.material.dto.MaterialDetailResponseDTO;
import com.werp.sero.material.dto.MaterialListResponseDTO;
import com.werp.sero.material.dto.MaterialUpdateRequestDTO;
import com.werp.sero.material.entity.Bom;
import com.werp.sero.material.entity.Material;
import com.werp.sero.material.exception.MaterialCodeDuplicatedException;
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
@DisplayName("MaterialService 단위 테스트")
class MaterialServiceImplTest {

    @Mock
    private MaterialRepository materialRepository;

    @Mock
    private BomRepository bomRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private MaterialServiceImpl materialService;

    private Employee testEmployee;
    private Material testMaterial;
    private Material testRawMaterial;

    @BeforeEach
    void setUp() {
        testEmployee = mock(Employee.class);
        lenient().when(testEmployee.getId()).thenReturn(1);
        lenient().when(testEmployee.getName()).thenReturn("테스트매니저");
        lenient().when(testEmployee.getEmail()).thenReturn("test@test.com");

        testRawMaterial = Material.builder()
                .id(2)
                .name("원자재A")
                .materialCode("RAW-001")
                .spec("원자재 스펙")
                .baseUnit("kg")
                .operationUnit("kg")
                .type("MAT_RAW")
                .status("MAT_NORMAL")
                .safetyStock(100)
                .employee(testEmployee)
                .bomList(new ArrayList<>())
                .build();

        testMaterial = Material.builder()
                .id(1)
                .name("완제품A")
                .materialCode("FIN-001")
                .spec("완제품 스펙")
                .baseUnit("EA")
                .operationUnit("BOX")
                .moq(100)
                .cycleTime(60)
                .unitPrice(10000L)
                .conversionRate(10)
                .safetyStock(50)
                .type("MAT_FG")
                .status("MAT_NORMAL")
                .employee(testEmployee)
                .bomList(new ArrayList<>())
                .build();
    }

    @Test
    @DisplayName("자재 목록 조회 - 성공")
    void getMaterialList_Success() {
        // given
        List<Material> materials = List.of(testMaterial, testRawMaterial);
        given(materialRepository.findByCondition(any(), any(), any())).willReturn(materials);

        // when
        List<MaterialListResponseDTO> result = materialService.getMaterialList(null, null, null);

        // then
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getName()).isEqualTo("완제품A");
        assertThat(result.get(1).getName()).isEqualTo("원자재A");
        verify(materialRepository, times(1)).findByCondition(any(), any(), any());
    }

    @Test
    @DisplayName("자재 목록 조회 - 타입 필터링")
    void getMaterialList_WithTypeFilter() {
        // given
        List<Material> materials = List.of(testMaterial);
        given(materialRepository.findByCondition(eq("MAT_FG"), any(), any())).willReturn(materials);

        // when
        List<MaterialListResponseDTO> result = materialService.getMaterialList("MAT_FG", null, null);

        // then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getType()).isEqualTo("MAT_FG");
        verify(materialRepository, times(1)).findByCondition(eq("MAT_FG"), any(), any());
    }

    @Test
    @DisplayName("자재 상세 조회 - 성공")
    void getMaterialDetail_Success() {
        // given
        Bom bom = Bom.builder()
                .id(1)
                .material(testMaterial)
                .rawMaterial(testRawMaterial)
                .requirement(5)
                .note("BOM 메모")
                .createdAt("2025-12-14 10:00:00")
                .build();
        testMaterial.addBom(bom);

        given(materialRepository.findByIdWithBom(1)).willReturn(Optional.of(testMaterial));

        // when
        MaterialDetailResponseDTO result = materialService.getMaterialDetail(1);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("완제품A");
        assertThat(result.getMaterialCode()).isEqualTo("FIN-001");
        assertThat(result.getBomList()).hasSize(1);
        assertThat(result.getBomList().get(0).getRequirement()).isEqualTo(5);
        verify(materialRepository, times(1)).findByIdWithBom(1);
    }

    @Test
    @DisplayName("자재 상세 조회 - 존재하지 않는 자재")
    void getMaterialDetail_NotFound() {
        // given
        given(materialRepository.findByIdWithBom(999)).willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> materialService.getMaterialDetail(999))
                .isInstanceOf(MaterialNotFoundException.class);
        verify(materialRepository, times(1)).findByIdWithBom(999);
    }

    @Test
    @DisplayName("자재 생성 - 성공 (BOM 없음)")
    void createMaterial_WithoutBom_Success() {
        // given
        MaterialCreateRequestDTO request = new MaterialCreateRequestDTO(
                "신규자재",
                "NEW-001",
                "신규 스펙",
                "EA",
                "EA",
                100,
                30,
                5000L,
                "http://image.url",
                1,
                20,
                "MAT_RAW",
                "MAT_NORMAL",
                null
        );

        given(materialRepository.existsByMaterialCode("NEW-001")).willReturn(false);
        given(employeeRepository.findById(1)).willReturn(Optional.of(testEmployee));
        given(materialRepository.save(any(Material.class))).willReturn(testRawMaterial);

        // when
        materialService.createMaterial(request, 1);

        // then
        verify(materialRepository, times(1)).existsByMaterialCode("NEW-001");
        verify(employeeRepository, times(1)).findById(1);
        verify(materialRepository, times(1)).save(any(Material.class));
        verify(bomRepository, never()).save(any(Bom.class));
    }

    @Test
    @DisplayName("자재 생성 - 성공 (BOM 포함)")
    void createMaterial_WithBom_Success() {
        // given
        List<MaterialCreateRequestDTO.BomRequest> bomRequests = List.of(
                new MaterialCreateRequestDTO.BomRequest(2, 5, "BOM 메모1"),
                new MaterialCreateRequestDTO.BomRequest(3, 3, "BOM 메모2")
        );

        MaterialCreateRequestDTO request = new MaterialCreateRequestDTO(
                "완제품",
                "FIN-002",
                "완제품 스펙",
                "BOX",
                "EA",
                100,
                60,
                10000L,
                "http://image.url",
                10,
                50,
                "MAT_FG",
                "MAT_NORMAL",
                bomRequests
        );

        given(materialRepository.existsByMaterialCode("FIN-002")).willReturn(false);
        given(employeeRepository.findById(1)).willReturn(Optional.of(testEmployee));
        given(materialRepository.save(any(Material.class))).willReturn(testMaterial);
        given(materialRepository.findById(2)).willReturn(Optional.of(testRawMaterial));
        given(materialRepository.findById(3)).willReturn(Optional.of(testRawMaterial));

        // when
        materialService.createMaterial(request, 1);

        // then
        verify(materialRepository, times(1)).existsByMaterialCode("FIN-002");
        verify(employeeRepository, times(1)).findById(1);
        verify(materialRepository, times(1)).save(any(Material.class));
        verify(materialRepository, times(2)).findById(anyInt());
    }

    @Test
    @DisplayName("자재 생성 - 자재코드 중복")
    void createMaterial_DuplicateCode() {
        // given
        MaterialCreateRequestDTO request = new MaterialCreateRequestDTO(
                "중복자재",
                "FIN-001",
                "스펙",
                "EA",
                "EA",
                100,
                30,
                5000L,
                null,
                1,
                20,
                "MAT_RAW",
                "MAT_NORMAL",
                null
        );

        given(materialRepository.existsByMaterialCode("FIN-001")).willReturn(true);

        // when & then
        assertThatThrownBy(() -> materialService.createMaterial(request, 1))
                .isInstanceOf(MaterialCodeDuplicatedException.class);
        verify(materialRepository, times(1)).existsByMaterialCode("FIN-001");
        verify(materialRepository, never()).save(any(Material.class));
    }

    @Test
    @DisplayName("자재 수정 - 성공")
    void updateMaterial_Success() {
        // given
        MaterialUpdateRequestDTO request = new MaterialUpdateRequestDTO(
                "수정된자재명",
                "수정된스펙",
                "BOX",
                "EA",
                200,
                45,
                15000L,
                "http://new-image.url",
                20,
                100,
                "MAT_NORMAL",
                null
        );

        given(materialRepository.findByIdWithBom(1)).willReturn(Optional.of(testMaterial));

        // when
        materialService.updateMaterial(1, request);

        // then
        verify(materialRepository, times(1)).findByIdWithBom(1);
        verify(bomRepository, never()).deleteByMaterialId(anyInt());
    }

    @Test
    @DisplayName("자재 수정 - 존재하지 않는 자재")
    void updateMaterial_NotFound() {
        // given
        MaterialUpdateRequestDTO request = new MaterialUpdateRequestDTO(
                "수정된자재명",
                "수정된스펙",
                "BOX",
                "EA",
                200,
                45,
                15000L,
                null,
                20,
                100,
                "MAT_NORMAL",
                null
        );

        given(materialRepository.findByIdWithBom(999)).willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> materialService.updateMaterial(999, request))
                .isInstanceOf(MaterialNotFoundException.class);
        verify(materialRepository, times(1)).findByIdWithBom(999);
    }

    @Test
    @DisplayName("자재 수정 - BOM 업데이트")
    void updateMaterial_WithBomUpdate() {
        // given
        List<MaterialUpdateRequestDTO.BomRequest> bomRequests = List.of(
                new MaterialUpdateRequestDTO.BomRequest(2, 10, "수정된 BOM")
        );

        MaterialUpdateRequestDTO request = new MaterialUpdateRequestDTO(
                "수정된자재명",
                "수정된스펙",
                "BOX",
                "EA",
                200,
                45,
                15000L,
                null,
                20,
                100,
                "MAT_NORMAL",
                bomRequests
        );

        given(materialRepository.findByIdWithBom(1)).willReturn(Optional.of(testMaterial));
        given(materialRepository.findById(2)).willReturn(Optional.of(testRawMaterial));

        // when
        materialService.updateMaterial(1, request);

        // then
        verify(materialRepository, times(1)).findByIdWithBom(1);
        verify(bomRepository, times(1)).deleteByMaterialId(1);
        verify(materialRepository, times(1)).findById(2);
    }

    @Test
    @DisplayName("자재 비활성화 - 성공")
    void deactivateMaterial_Success() {
        // given
        given(materialRepository.findById(1)).willReturn(Optional.of(testMaterial));

        // when
        materialService.deactivateMaterial(1);

        // then
        verify(materialRepository, times(1)).findById(1);
    }

    @Test
    @DisplayName("자재 비활성화 - 존재하지 않는 자재")
    void deactivateMaterial_NotFound() {
        // given
        given(materialRepository.findById(999)).willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> materialService.deactivateMaterial(999))
                .isInstanceOf(MaterialNotFoundException.class);
        verify(materialRepository, times(1)).findById(999);
    }
}
