package com.werp.sero.client.service;

import com.werp.sero.client.dto.ClientAddressResponseDTO;
import com.werp.sero.client.dto.ClientInfoResponseDTO;
import com.werp.sero.client.entity.Client;
import com.werp.sero.client.entity.ClientAddress;
import com.werp.sero.client.exception.ClientNotFoundException;
import com.werp.sero.client.repository.ClientAddressRepository;
import com.werp.sero.client.repository.ClientRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("ClientServiceImpl 테스트")
class ClientServiceImplTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ClientAddressRepository clientAddressRepository;

    @InjectMocks
    private ClientServiceImpl clientService;

    @Test
    @DisplayName("고객사 기본 정보 조회 - 성공")
    void getClientInfo_Success() {
        // given
        int clientId = 1;
        Client client = mock(Client.class);
        when(client.getId()).thenReturn(clientId);
        when(client.getCompanyName()).thenReturn("(주)테스트고객사");
        when(client.getCeoName()).thenReturn("김대표");
        when(client.getCompanyContact()).thenReturn("02-1234-5678");
        when(client.getBusinessNo()).thenReturn("123-45-67890");
        when(client.getBusinessType()).thenReturn("제조업");
        when(client.getBusinessItem()).thenReturn("자동차부품");
        when(client.getAddress()).thenReturn("서울특별시 강남구 테헤란로 123");

        given(clientRepository.findById(clientId)).willReturn(Optional.of(client));

        // when
        ClientInfoResponseDTO result = clientService.getClientInfo(clientId);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(clientId);
        assertThat(result.getCompanyName()).isEqualTo("(주)테스트고객사");
        assertThat(result.getCeoName()).isEqualTo("김대표");
        assertThat(result.getCompanyContact()).isEqualTo("02-1234-5678");
        assertThat(result.getBusinessNo()).isEqualTo("123-45-67890");
        assertThat(result.getBusinessType()).isEqualTo("제조업");
        assertThat(result.getBusinessItem()).isEqualTo("자동차부품");
        assertThat(result.getAddress()).isEqualTo("서울특별시 강남구 테헤란로 123");
    }

    @Test
    @DisplayName("고객사 기본 정보 조회 - 존재하지 않는 고객사")
    void getClientInfo_NotFound() {
        // given
        int clientId = 999;
        given(clientRepository.findById(clientId)).willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> clientService.getClientInfo(clientId))
                .isInstanceOf(ClientNotFoundException.class);
    }

    @Test
    @DisplayName("고객사 배송지 목록 조회 - 성공")
    void getClientAddresses_Success() {
        // given
        int clientId = 1;

        // 첫 번째 배송지 (기본 배송지)
        ClientAddress address1 = mock(ClientAddress.class);
        when(address1.getId()).thenReturn(1);
        when(address1.getName()).thenReturn("본사");
        when(address1.getAddress()).thenReturn("서울특별시 강남구 테헤란로 123");
        when(address1.getLatitude()).thenReturn(37.5012);
        when(address1.getLongitude()).thenReturn(127.0396);
        when(address1.getRecipientName()).thenReturn("김수령");
        when(address1.getRecipientContact()).thenReturn("02-1234-5678");
        when(address1.isDefault()).thenReturn(true);

        // 두 번째 배송지
        ClientAddress address2 = mock(ClientAddress.class);
        when(address2.getId()).thenReturn(2);
        when(address2.getName()).thenReturn("경기 물류센터");
        when(address2.getAddress()).thenReturn("경기도 용인시 처인구 포곡읍 에버랜드로 199");
        when(address2.getLatitude()).thenReturn(37.2945);
        when(address2.getLongitude()).thenReturn(127.2018);
        when(address2.getRecipientName()).thenReturn("이담당");
        when(address2.getRecipientContact()).thenReturn("031-9876-5432");
        when(address2.isDefault()).thenReturn(false);

        List<ClientAddress> addresses = Arrays.asList(address1, address2);

        given(clientRepository.existsById(clientId)).willReturn(true);
        given(clientAddressRepository.findByClientIdOrderByDefault(clientId)).willReturn(addresses);

        // when
        List<ClientAddressResponseDTO> result = clientService.getClientAddresses(clientId);

        // then
        assertThat(result).hasSize(2);

        // 기본 배송지가 첫 번째로 정렬되어 있는지 확인
        ClientAddressResponseDTO firstAddress = result.get(0);
        assertThat(firstAddress.getId()).isEqualTo(1);
        assertThat(firstAddress.getName()).isEqualTo("본사");
        assertThat(firstAddress.isDefault()).isTrue();

        ClientAddressResponseDTO secondAddress = result.get(1);
        assertThat(secondAddress.getId()).isEqualTo(2);
        assertThat(secondAddress.getName()).isEqualTo("경기 물류센터");
        assertThat(secondAddress.isDefault()).isFalse();
    }

    @Test
    @DisplayName("고객사 배송지 목록 조회 - 존재하지 않는 고객사")
    void getClientAddresses_ClientNotFound() {
        // given
        int clientId = 999;
        given(clientRepository.existsById(clientId)).willReturn(false);

        // when & then
        assertThatThrownBy(() -> clientService.getClientAddresses(clientId))
                .isInstanceOf(ClientNotFoundException.class);
    }

    @Test
    @DisplayName("고객사 배송지 목록 조회 - 빈 리스트")
    void getClientAddresses_EmptyList() {
        // given
        int clientId = 1;
        given(clientRepository.existsById(clientId)).willReturn(true);
        given(clientAddressRepository.findByClientIdOrderByDefault(clientId)).willReturn(List.of());

        // when
        List<ClientAddressResponseDTO> result = clientService.getClientAddresses(clientId);

        // then
        assertThat(result).isEmpty();
    }
}
