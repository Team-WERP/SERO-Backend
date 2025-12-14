package com.werp.sero.material.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.werp.sero.material.dto.MaterialCreateRequestDTO;
import com.werp.sero.material.dto.MaterialDetailResponseDTO;
import com.werp.sero.material.dto.MaterialListResponseDTO;
import com.werp.sero.material.dto.MaterialUpdateRequestDTO;
import com.werp.sero.material.exception.MaterialCodeDuplicatedException;
import com.werp.sero.material.exception.MaterialNotFoundException;
import com.werp.sero.material.service.MaterialService;
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

@WebMvcTest(MaterialController.class)
@DisplayName("MaterialController 통합 테스트")
class MaterialControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private MaterialService materialService;

    @Test
    @DisplayName("GET /api/materials - 전체 조회 성공")
    void getMaterials_Success() throws Exception {
        // given
        List<MaterialListResponseDTO> materials = List.of(
                MaterialListResponseDTO.builder()
                        .id(1)
                        .name("완제품A")
                        .materialCode("FIN-001")
                        .type("MAT_FINISH")
                        .status("MAT_NORMAL")
                        .unitPrice(10000L)
                        .build(),
                MaterialListResponseDTO.builder()
                        .id(2)
                        .name("원자재A")
                        .materialCode("RAW-001")
                        .type("MAT_RAW")
                        .status("MAT_NORMAL")
                        .unitPrice(1000L)
                        .build()
        );
        given(materialService.getMaterialList(null, null, null)).willReturn(materials);

        // when & then
        mockMvc.perform(get("/api/materials"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("완제품A"))
                .andExpect(jsonPath("$[0].materialCode").value("FIN-001"))
                .andExpect(jsonPath("$[1].name").value("원자재A"));

        verify(materialService, times(1)).getMaterialList(null, null, null);
    }

    @Test
    @DisplayName("GET /api/materials - 타입 필터링")
    void getMaterials_WithTypeFilter() throws Exception {
        // given
        List<MaterialListResponseDTO> materials = List.of(
                MaterialListResponseDTO.builder()
                        .id(2)
                        .name("원자재A")
                        .materialCode("RAW-001")
                        .type("MAT_RAW")
                        .status("MAT_NORMAL")
                        .unitPrice(1000L)
                        .build()
        );
        given(materialService.getMaterialList("MAT_RAW", null, null)).willReturn(materials);

        // when & then
        mockMvc.perform(get("/api/materials")
                        .param("type", "MAT_RAW"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].type").value("MAT_RAW"));

        verify(materialService, times(1)).getMaterialList("MAT_RAW", null, null);
    }

    @Test
    @DisplayName("GET /api/materials/{id} - 상세 조회 성공")
    void getMaterial_Success() throws Exception {
        // given
        MaterialDetailResponseDTO material = MaterialDetailResponseDTO.builder()
                .id(1)
                .name("완제품A")
                .materialCode("FIN-001")
                .spec("완제품 스펙")
                .baseUnit("EA")
                .type("MAT_FINISH")
                .status("MAT_NORMAL")
                .unitPrice(10000L)
                .safetyStock(50)
                .build();
        given(materialService.getMaterialDetail(1)).willReturn(material);

        // when & then
        mockMvc.perform(get("/api/materials/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("완제품A"))
                .andExpect(jsonPath("$.materialCode").value("FIN-001"))
                .andExpect(jsonPath("$.spec").value("완제품 스펙"));

        verify(materialService, times(1)).getMaterialDetail(1);
    }

    @Test
    @DisplayName("GET /api/materials/{id} - 존재하지 않는 자재")
    void getMaterial_NotFound() throws Exception {
        // given
        given(materialService.getMaterialDetail(999))
                .willThrow(new MaterialNotFoundException("해당 자재를 찾을 수 없습니다. ID: 999"));

        // when & then
        mockMvc.perform(get("/api/materials/999"))
                .andDo(print())
                .andExpect(status().is4xxClientError());

        verify(materialService, times(1)).getMaterialDetail(999);
    }

    @Test
    @DisplayName("POST /api/materials - 자재 생성 성공")
    void createMaterial_Success() throws Exception {
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
                null,
                1,
                20,
                "MAT_RAW",
                "MAT_NORMAL",
                null
        );
        willDoNothing().given(materialService).createMaterial(any(), eq(5));

        // when & then
        mockMvc.perform(post("/api/materials")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk());

        verify(materialService, times(1)).createMaterial(any(MaterialCreateRequestDTO.class), eq(5));
    }

    @Test
    @DisplayName("POST /api/materials - 자재 코드 중복")
    void createMaterial_DuplicateCode() throws Exception {
        // given
        MaterialCreateRequestDTO request = new MaterialCreateRequestDTO(
                "중복자재",
                "DUP-001",
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
        willThrow(new MaterialCodeDuplicatedException("이미 존재하는 자재 코드입니다: DUP-001"))
                .given(materialService).createMaterial(any(), eq(5));

        // when & then
        mockMvc.perform(post("/api/materials")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().is4xxClientError());

        verify(materialService, times(1)).createMaterial(any(MaterialCreateRequestDTO.class), eq(5));
    }

    @Test
    @DisplayName("PUT /api/materials/{id} - 자재 수정 성공")
    void updateMaterial_Success() throws Exception {
        // given
        MaterialUpdateRequestDTO request = new MaterialUpdateRequestDTO(
                "수정된자재",
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
        willDoNothing().given(materialService).updateMaterial(eq(1), any());

        // when & then
        mockMvc.perform(put("/api/materials/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk());

        verify(materialService, times(1)).updateMaterial(eq(1), any(MaterialUpdateRequestDTO.class));
    }

    @Test
    @DisplayName("PUT /api/materials/{id} - 존재하지 않는 자재 수정")
    void updateMaterial_NotFound() throws Exception {
        // given
        MaterialUpdateRequestDTO request = new MaterialUpdateRequestDTO(
                "수정된자재",
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
        willThrow(new MaterialNotFoundException("해당 자재를 찾을 수 없습니다. ID: 999"))
                .given(materialService).updateMaterial(eq(999), any());

        // when & then
        mockMvc.perform(put("/api/materials/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().is4xxClientError());

        verify(materialService, times(1)).updateMaterial(eq(999), any(MaterialUpdateRequestDTO.class));
    }

    @Test
    @DisplayName("PATCH /api/materials/{id}/deactivate - 자재 비활성화 성공")
    void deactivateMaterial_Success() throws Exception {
        // given
        willDoNothing().given(materialService).deactivateMaterial(1);

        // when & then
        mockMvc.perform(patch("/api/materials/1/deactivate"))
                .andDo(print())
                .andExpect(status().isOk());

        verify(materialService, times(1)).deactivateMaterial(1);
    }

    @Test
    @DisplayName("PATCH /api/materials/{id}/deactivate - 존재하지 않는 자재 비활성화")
    void deactivateMaterial_NotFound() throws Exception {
        // given
        willThrow(new MaterialNotFoundException("해당 자재를 찾을 수 없습니다. ID: 999"))
                .given(materialService).deactivateMaterial(999);

        // when & then
        mockMvc.perform(patch("/api/materials/999/deactivate"))
                .andDo(print())
                .andExpect(status().is4xxClientError());

        verify(materialService, times(1)).deactivateMaterial(999);
    }
}
