package com.werp.sero.material.repository;

import com.werp.sero.material.entity.Bom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BomRepository extends JpaRepository<Bom, Integer> {
    void deleteByMaterialId(int materialId);
}
