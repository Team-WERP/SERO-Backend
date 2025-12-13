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
import com.werp.sero.material.repository.WarehouseStockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MaterialServiceImpl implements MaterialService{

    private final MaterialRepository materialRepository;
    private final BomRepository bomRepository;
    private final WarehouseStockRepository warehouseStockRepository;
    private final EmployeeRepository employeeRepository;


    /* ================= 조회 ================= */

    @Override
    @Transactional(readOnly = true)
    public List<MaterialListResponseDTO> getMaterialList(
            String type, String status, String keyword) {

        List<Material> materials =
                materialRepository.findByCondition(type, status, keyword);

        return materials.stream()
                .map(MaterialListResponseDTO::from)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public MaterialDetailResponseDTO getMaterialDetail(int materialId) {

        Material material = materialRepository.findByIdWithBom(materialId)
                .orElseThrow(MaterialNotFoundException::new);

        return MaterialDetailResponseDTO.from(material);
    }

    @Override
    public void createMaterial(MaterialCreateRequestDTO request, int loginEmployeeId) {
        // 1. 자재 코드 중복 체크
        if (materialRepository.existsByMaterialCode(request.getMaterialCode())) {
            throw new MaterialCodeDuplicatedException();
        }

        // 2. 담당자 조회
        Employee manager = employeeRepository.findById(loginEmployeeId)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found"));

        // 3. 자재 생성
        Material material = Material.builder()
                .name(request.getName())
                .materialCode(request.getMaterialCode())
                .spec(request.getSpec())
                .operationUnit(request.getOperationUnit())
                .baseUnit(request.getBaseUnit())
                .moq(request.getMoq() != null ? request.getMoq() : 0)
                .cycleTime(request.getCycleTime() != null ? request.getCycleTime() : 0)
                .unitPrice(request.getUnitPrice() != null ? request.getUnitPrice() : 0)
                .imageUrl(request.getImageUrl())
                .conversionRate(request.getConversionRate() != null ? request.getConversionRate() : 0)
                .safetyStock(request.getSafetyStock() != null ? request.getSafetyStock() : 1)
                .rawMaterialCount(0)
                .type(request.getType())
                .status(request.getStatus() != null ? request.getStatus() : "MAT_NORMAL")
                .employee(manager)
                .createdAt(getCurrentTimestamp())
                .build();

        // 4. 자재 저장
        Material savedMaterial = materialRepository.save(material);

        // 5. BOM 생성 (완제품인 경우)
        if ("MAT_FG".equals(request.getType()) && request.getBomList() != null) {
            for (MaterialCreateRequestDTO.BomRequest bomRequest : request.getBomList()) {
                Material rawMaterial = materialRepository.findById(bomRequest.getRawMaterialId())
                        .orElseThrow(MaterialNotFoundException::new);

                Bom bom = Bom.builder()
                        .material(savedMaterial)
                        .rawMaterial(rawMaterial)
                        .requirement(bomRequest.getRequirement())
                        .note(bomRequest.getNote())
                        .createdAt(getCurrentTimestamp())
                        .build();

                savedMaterial.addBom(bom);
            }
        }
    }

    @Override
    public void updateMaterial(int materialId, MaterialUpdateRequestDTO request) {
        // 1. 자재 조회
        Material material = materialRepository.findByIdWithBom(materialId)
                .orElseThrow(MaterialNotFoundException::new);

        // 2. 자재 정보 수정
        material.update(
                request.getName(),
                request.getSpec(),
                request.getOperationUnit(),
                request.getBaseUnit(),
                request.getMoq(),
                request.getCycleTime(),
                request.getUnitPrice(),
                request.getImageUrl(),
                request.getConversionRate(),
                request.getSafetyStock(),
                request.getStatus()
        );

        // 3. BOM 수정 (완제품인 경우)
        if ("MAT_FG".equals(material.getType()) && request.getBomList() != null) {
            // 기존 BOM 삭제
            bomRepository.deleteByMaterialId(materialId);

            // 새로운 BOM 생성 (Builder 패턴)
            List<Bom> newBomList = new ArrayList<>();
            for (MaterialUpdateRequestDTO.BomRequest bomRequest : request.getBomList()) {
                Material rawMaterial = materialRepository.findById(bomRequest.getRawMaterialId())
                        .orElseThrow(MaterialNotFoundException::new);

                Bom bom = Bom.builder()
                        .material(material)
                        .rawMaterial(rawMaterial)
                        .requirement(bomRequest.getRequirement())
                        .note(bomRequest.getNote())
                        .createdAt(getCurrentTimestamp())
                        .build();

                newBomList.add(bom);
            }

            material.replaceBomList(newBomList);
        }
    }

    private String getCurrentTimestamp() {
        return java.time.LocalDateTime.now()
                .format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    @Override
    public void deactivateMaterial(int materialId) {
        Material material = materialRepository.findById(materialId)
                .orElseThrow(MaterialNotFoundException::new);

        material.deactivate();
    }
}
