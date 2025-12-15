package com.werp.sero.material.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.werp.sero.material.dto.BomExplosionRequestDTO;
import com.werp.sero.material.dto.BomExplosionResponseDTO;
import com.werp.sero.material.dto.BomImplosionResponseDTO;
import com.werp.sero.material.dto.MaterialSearchResponseDTO;
import com.werp.sero.material.exception.MaterialNotFoundException;
import com.werp.sero.material.service.BomCalculationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BomCalculationController.class)
@DisplayName("BomCalculationController 통합 테스트")
class BomCalculationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private BomCalculationService bomCalculationService;

    @Test
    @DisplayName("GET /bom/materials/search - 자재 검색 성공")
    void searchMaterials_Success() throws Exception {
        // given
        List<MaterialSearchResponseDTO> materials = List.of(
                MaterialSearchResponseDTO.builder()
                        .id(1)
                        .name("완제품A")
                        .materialCode("FIN-001")
                        .spec("완제품 스펙")
                        .type("MAT_FINISH")
                        .baseUnit("EA")
                        .build(),
                MaterialSearchResponseDTO.builder()
                        .id(2)
                        .name("원자재A")
                        .materialCode("RAW-001")
                        .spec("원자재 스펙")
                        .type("MAT_RAW")
                        .baseUnit("kg")
                        .build()
        );
        given(bomCalculationService.searchMaterials(anyString(), any())).willReturn(materials);

        // when & then
        mockMvc.perform(get("/bom/materials/search")
                        .param("keyword", "자재"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("완제품A"))
                .andExpect(jsonPath("$[1].name").value("원자재A"));

        verify(bomCalculationService, times(1)).searchMaterials(anyString(), any());
    }

    @Test
    @DisplayName("GET /bom/materials/search - 타입 필터링")
    void searchMaterials_WithTypeFilter() throws Exception {
        // given
        List<MaterialSearchResponseDTO> materials = List.of(
                MaterialSearchResponseDTO.builder()
                        .id(2)
                        .name("원자재A")
                        .materialCode("RAW-001")
                        .spec("원자재 스펙")
                        .type("MAT_RAW")
                        .baseUnit("kg")
                        .build()
        );
        given(bomCalculationService.searchMaterials(any(), anyString())).willReturn(materials);

        // when & then
        mockMvc.perform(get("/bom/materials/search")
                        .param("type", "MAT_RAW"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].type").value("MAT_RAW"));

        verify(bomCalculationService, times(1)).searchMaterials(any(), anyString());
    }

    @Test
    @DisplayName("POST /bom/explosion - BOM 정전개 성공")
    void calculateExplosion_Success() throws Exception {
        // given
        BomExplosionRequestDTO request = new BomExplosionRequestDTO(1, 100);

        BomExplosionResponseDTO.MaterialInfo finishedGood = BomExplosionResponseDTO.MaterialInfo.builder()
                .id(1)
                .name("완제품A")
                .materialCode("FIN-001")
                .spec("완제품 스펙")
                .type("MAT_FINISH")
                .baseUnit("EA")
                .build();

        List<BomExplosionResponseDTO.RequiredMaterial> requiredMaterials = List.of(
                BomExplosionResponseDTO.RequiredMaterial.builder()
                        .materialId(2)
                        .materialName("원자재A")
                        .materialCode("RAW-001")
                        .spec("원자재 스펙")
                        .baseUnit("kg")
                        .unitRequirement(5)
                        .totalRequirement(500)
                        .unitPrice(1000L)
                        .totalPrice(500000L)
                        .build(),
                BomExplosionResponseDTO.RequiredMaterial.builder()
                        .materialId(3)
                        .materialName("원자재B")
                        .materialCode("RAW-002")
                        .spec("원자재B 스펙")
                        .baseUnit("L")
                        .unitRequirement(3)
                        .totalRequirement(300)
                        .unitPrice(2000L)
                        .totalPrice(600000L)
                        .build()
        );

        BomExplosionResponseDTO response = BomExplosionResponseDTO.builder()
                .finishedGood(finishedGood)
                .requestedQuantity(100)
                .requiredMaterials(requiredMaterials)
                .totalMaterialCount(2)
                .build();

        given(bomCalculationService.calculateExplosion(any(BomExplosionRequestDTO.class)))
                .willReturn(response);

        // when & then
        mockMvc.perform(post("/bom/explosion")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.finishedGood.name").value("완제품A"))
                .andExpect(jsonPath("$.requestedQuantity").value(100))
                .andExpect(jsonPath("$.totalMaterialCount").value(2))
                .andExpect(jsonPath("$.requiredMaterials.length()").value(2))
                .andExpect(jsonPath("$.requiredMaterials[0].materialName").value("원자재A"))
                .andExpect(jsonPath("$.requiredMaterials[0].totalRequirement").value(500))
                .andExpect(jsonPath("$.requiredMaterials[0].totalPrice").value(500000))
                .andExpect(jsonPath("$.requiredMaterials[1].materialName").value("원자재B"))
                .andExpect(jsonPath("$.requiredMaterials[1].totalRequirement").value(300))
                .andExpect(jsonPath("$.requiredMaterials[1].totalPrice").value(600000));

        verify(bomCalculationService, times(1)).calculateExplosion(any(BomExplosionRequestDTO.class));
    }

    @Test
    @DisplayName("POST /bom/explosion - 존재하지 않는 완제품")
    void calculateExplosion_MaterialNotFound() throws Exception {
        // given
        BomExplosionRequestDTO request = new BomExplosionRequestDTO(999, 100);
        given(bomCalculationService.calculateExplosion(any(BomExplosionRequestDTO.class)))
                .willThrow(new MaterialNotFoundException("해당 자재를 찾을 수 없습니다. ID: 999"));

        // when & then
        mockMvc.perform(post("/bom/explosion")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().is4xxClientError());

        verify(bomCalculationService, times(1)).calculateExplosion(any(BomExplosionRequestDTO.class));
    }

    @Test
    @DisplayName("GET /bom/implosion/{rawMaterialId} - BOM 역전개 성공")
    void calculateImplosion_Success() throws Exception {
        // given
        BomImplosionResponseDTO.MaterialInfo rawMaterial = BomImplosionResponseDTO.MaterialInfo.builder()
                .id(2)
                .name("원자재A")
                .materialCode("RAW-001")
                .spec("원자재 스펙")
                .type("MAT_RAW")
                .baseUnit("kg")
                .unitPrice(1000L)
                .build();

        List<BomImplosionResponseDTO.UsedInProduct> usedInProducts = List.of(
                BomImplosionResponseDTO.UsedInProduct.builder()
                        .productId(1)
                        .productName("완제품A")
                        .productCode("FIN-001")
                        .productSpec("완제품 스펙")
                        .baseUnit("EA")
                        .requirement(5)
                        .productUnitPrice(10000L)
                        .build(),
                BomImplosionResponseDTO.UsedInProduct.builder()
                        .productId(3)
                        .productName("완제품B")
                        .productCode("FIN-002")
                        .productSpec("완제품B 스펙")
                        .baseUnit("EA")
                        .requirement(3)
                        .productUnitPrice(8000L)
                        .build()
        );

        BomImplosionResponseDTO response = BomImplosionResponseDTO.builder()
                .rawMaterial(rawMaterial)
                .usedInProducts(usedInProducts)
                .totalProductCount(2)
                .build();

        given(bomCalculationService.calculateImplosion(2)).willReturn(response);

        // when & then
        mockMvc.perform(get("/bom/implosion/2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rawMaterial.name").value("원자재A"))
                .andExpect(jsonPath("$.totalProductCount").value(2))
                .andExpect(jsonPath("$.usedInProducts.length()").value(2))
                .andExpect(jsonPath("$.usedInProducts[0].productName").value("완제품A"))
                .andExpect(jsonPath("$.usedInProducts[0].requirement").value(5))
                .andExpect(jsonPath("$.usedInProducts[1].productName").value("완제품B"))
                .andExpect(jsonPath("$.usedInProducts[1].requirement").value(3));

        verify(bomCalculationService, times(1)).calculateImplosion(2);
    }

    @Test
    @DisplayName("GET /bom/implosion/{rawMaterialId} - 존재하지 않는 원자재")
    void calculateImplosion_MaterialNotFound() throws Exception {
        // given
        given(bomCalculationService.calculateImplosion(999))
                .willThrow(new MaterialNotFoundException("해당 자재를 찾을 수 없습니다. ID: 999"));

        // when & then
        mockMvc.perform(get("/bom/implosion/999"))
                .andDo(print())
                .andExpect(status().is4xxClientError());

        verify(bomCalculationService, times(1)).calculateImplosion(999);
    }

    @Test
    @DisplayName("GET /bom/implosion/{rawMaterialId} - 사용처가 없는 원자재")
    void calculateImplosion_NoUsage() throws Exception {
        // given
        BomImplosionResponseDTO.MaterialInfo rawMaterial = BomImplosionResponseDTO.MaterialInfo.builder()
                .id(2)
                .name("원자재A")
                .materialCode("RAW-001")
                .spec("원자재 스펙")
                .type("MAT_RAW")
                .baseUnit("kg")
                .unitPrice(1000L)
                .build();

        BomImplosionResponseDTO response = BomImplosionResponseDTO.builder()
                .rawMaterial(rawMaterial)
                .usedInProducts(List.of())
                .totalProductCount(0)
                .build();

        given(bomCalculationService.calculateImplosion(2)).willReturn(response);

        // when & then
        mockMvc.perform(get("/bom/implosion/2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rawMaterial.name").value("원자재A"))
                .andExpect(jsonPath("$.totalProductCount").value(0))
                .andExpect(jsonPath("$.usedInProducts.length()").value(0));

        verify(bomCalculationService, times(1)).calculateImplosion(2);
    }
}
