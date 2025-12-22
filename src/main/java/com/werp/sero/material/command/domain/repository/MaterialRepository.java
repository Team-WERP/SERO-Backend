package com.werp.sero.material.command.domain.repository;

import com.werp.sero.material.command.domain.aggregate.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 자재 Command Repository 인터페이스
 */
public interface MaterialRepository extends JpaRepository<Material, Integer> {

    boolean existsByMaterialCode(String materialCode);

    Optional<Material> findByMaterialCode(String materialCode);
}
