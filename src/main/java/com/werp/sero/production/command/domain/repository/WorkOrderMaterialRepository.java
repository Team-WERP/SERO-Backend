package com.werp.sero.production.command.domain.repository;

import com.werp.sero.production.command.domain.aggregate.WorkOrderMaterial;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WorkOrderMaterialRepository extends JpaRepository<WorkOrderMaterial, Integer> {

    List<WorkOrderMaterial> findByWorkOrder_Id(int workOrderId);

    Optional<WorkOrderMaterial> findByWorkOrder_IdAndRawMaterial_Id(int woId, int rawMaterialId);
}
