package com.werp.sero.client.repository;

import com.werp.sero.client.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientInfoRepository extends JpaRepository<Client, Integer> {

    /* 설명. 사업자등록번호로 고객사 조회 */
    Optional<Client> findByBusinessNo(String businessNo);

    /* 설명. 사업자등록번호 중복 확인 */
    boolean existsByBusinessNo(String businessNo);
}
