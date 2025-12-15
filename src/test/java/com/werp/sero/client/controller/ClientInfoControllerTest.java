package com.werp.sero.client.controller;

import com.werp.sero.client.dto.ClientAddressResponseDTO;
import com.werp.sero.client.dto.ClientInfoResponseDTO;
import com.werp.sero.client.service.ClientInfoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ClientInfoController.class)
@DisplayName("ClientController 테스트")
class ClientInfoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ClientInfoService clientInfoService;

    @Test
    @DisplayName("고객사 기본 정보 조회 - 성공")
    void getClientInfo_Success() throws Exception {
        // given
        int clientId = 1;
        ClientInfoResponseDTO response = ClientInfoResponseDTO.builder()
                .id(clientId)
                .companyName("(주)테스트고객사")
                .ceoName("김대표")
                .companyContact("02-1234-5678")
                .businessNo("123-45-67890")
                .businessType("제조업")
                .businessItem("자동차부품")
                .address("서울특별시 강남구 테헤란로 123")
                .build();

        given(clientInfoService.getClientInfo(clientId)).willReturn(response);

        // when & then
        mockMvc.perform(get("/clients/{id}", clientId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(clientId))
                .andExpect(jsonPath("$.companyName").value("(주)테스트고객사"))
                .andExpect(jsonPath("$.ceoName").value("김대표"))
                .andExpect(jsonPath("$.companyContact").value("02-1234-5678"))
                .andExpect(jsonPath("$.businessNo").value("123-45-67890"))
                .andExpect(jsonPath("$.businessType").value("제조업"))
                .andExpect(jsonPath("$.businessItem").value("자동차부품"))
                .andExpect(jsonPath("$.address").value("서울특별시 강남구 테헤란로 123"));
    }

    @Test
    @DisplayName("고객사 배송지 목록 조회 - 성공")
    void getClientAddresses_Success() throws Exception {
        // given
        int clientId = 1;
        List<ClientAddressResponseDTO> addresses = Arrays.asList(
                ClientAddressResponseDTO.builder()
                        .id(1)
                        .name("본사")
                        .address("서울특별시 강남구 테헤란로 123")
                        .latitude(37.5012)
                        .longitude(127.0396)
                        .recipientName("김수령")
                        .recipientContact("02-1234-5678")
                        .isDefault(true)
                        .build(),
                ClientAddressResponseDTO.builder()
                        .id(2)
                        .name("경기 물류센터")
                        .address("경기도 용인시 처인구 포곡읍 에버랜드로 199")
                        .latitude(37.2945)
                        .longitude(127.2018)
                        .recipientName("이담당")
                        .recipientContact("031-9876-5432")
                        .isDefault(false)
                        .build()
        );

        given(clientInfoService.getClientAddresses(clientId)).willReturn(addresses);

        // when & then
        mockMvc.perform(get("/clients/{id}/addresses", clientId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("본사"))
                .andExpect(jsonPath("$[0].address").value("서울특별시 강남구 테헤란로 123"))
                .andExpect(jsonPath("$[0].default").value(true))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("경기 물류센터"))
                .andExpect(jsonPath("$[1].default").value(false));
    }

    @Test
    @DisplayName("고객사 배송지 목록 조회 - 빈 리스트")
    void getClientAddresses_EmptyList() throws Exception {
        // given
        int clientId = 999;
        given(clientInfoService.getClientAddresses(clientId)).willReturn(List.of());

        // when & then
        mockMvc.perform(get("/clients/{id}/addresses", clientId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));
    }
}
