package com.werp.sero.material.query.service;

import com.werp.sero.material.exception.InvalidMaterialTypeException;
import com.werp.sero.material.exception.MaterialNotFoundException;
import com.werp.sero.material.command.domain.aggregate.Bom;
import com.werp.sero.material.command.domain.aggregate.Material;
import com.werp.sero.material.query.dao.BomMapper;
import com.werp.sero.material.query.dao.MaterialMapper;
import com.werp.sero.material.query.dto.BomExplosionRequestDTO;
import com.werp.sero.material.query.dto.BomExplosionResponseDTO;
import com.werp.sero.material.query.dto.BomImplosionResponseDTO;
import com.werp.sero.material.query.dto.MaterialSearchResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BomCalculationQueryServiceImpl implements BomCalculationQueryService {

    private final MaterialMapper materialMapper;
    private final BomMapper bomMapper;

    // 허용되는 자재 유형
    private static final List<String> VALID_MATERIAL_TYPES = Arrays.asList("MAT_FG", "MAT_RM");

    @Override
    public List<MaterialSearchResponseDTO> searchMaterials(String keyword, String type) {
        // 1. 자재 유형 유효성 검증 (값이 있을 경우에만)
        if (type != null && !type.isEmpty() && !VALID_MATERIAL_TYPES.contains(type)) {
            throw new InvalidMaterialTypeException();
        }

        // 2. 자재 검색
        List<Material> materials = materialMapper.findByCondition(type, null, keyword);

        return materials.stream()
                .map(MaterialSearchResponseDTO::from)
                .toList();
    }

    @Override
    public BomExplosionResponseDTO calculateExplosion(BomExplosionRequestDTO request) {
        // 1. 완제품 정보 조회
        Material finishedGood = materialMapper.findById(request.getMaterialId())
                .orElseThrow(MaterialNotFoundException::new);

        // 2. BOM 조회 (완제품에 필요한 원부자재 목록)
        List<Bom> bomList = bomMapper.findByMaterialIdWithRawMaterial(request.getMaterialId());

        // 3. 소요량 계산
        List<BomExplosionResponseDTO.RequiredMaterial> requiredMaterials = bomList.stream()
                .map(bom -> {
                    Material rawMaterial = bom.getRawMaterial();
                    int totalRequirement = bom.getRequirement() * request.getQuantity();
                    long totalPrice = rawMaterial.getUnitPrice() * totalRequirement;

                    return BomExplosionResponseDTO.RequiredMaterial.builder()
                            .materialId(rawMaterial.getId())
                            .materialName(rawMaterial.getName())
                            .materialCode(rawMaterial.getMaterialCode())
                            .spec(rawMaterial.getSpec())
                            .baseUnit(rawMaterial.getBaseUnit())
                            .unitRequirement(bom.getRequirement())
                            .totalRequirement(totalRequirement)
                            .unitPrice(rawMaterial.getUnitPrice())
                            .totalPrice(totalPrice)
                            .build();
                })
                .toList();

        // 4. 완제품 정보 DTO 생성
        BomExplosionResponseDTO.MaterialInfo materialInfo = BomExplosionResponseDTO.MaterialInfo.builder()
                .id(finishedGood.getId())
                .name(finishedGood.getName())
                .materialCode(finishedGood.getMaterialCode())
                .spec(finishedGood.getSpec())
                .type(finishedGood.getType())
                .baseUnit(finishedGood.getBaseUnit())
                .build();

        // 5. 응답 DTO 생성
        return BomExplosionResponseDTO.builder()
                .finishedGood(materialInfo)
                .requestedQuantity(request.getQuantity())
                .requiredMaterials(requiredMaterials)
                .totalMaterialCount(requiredMaterials.size())
                .build();
    }

    @Override
    public BomImplosionResponseDTO calculateImplosion(int rawMaterialId) {
        // 1. 원부자재 정보 조회
        Material rawMaterial = materialMapper.findById(rawMaterialId)
                .orElseThrow(MaterialNotFoundException::new);

        // 2. 해당 원자재를 사용하는 완제품 BOM 조회
        List<Bom> bomList = bomMapper.findByRawMaterialIdWithMaterial(rawMaterialId);

        // 3. 사용처 완제품 목록 생성
        List<BomImplosionResponseDTO.UsedInProduct> usedInProducts = bomList.stream()
                .map(bom -> {
                    Material product = bom.getMaterial();

                    return BomImplosionResponseDTO.UsedInProduct.builder()
                            .productId(product.getId())
                            .productName(product.getName())
                            .productCode(product.getMaterialCode())
                            .productSpec(product.getSpec())
                            .baseUnit(product.getBaseUnit())
                            .requirement(bom.getRequirement())
                            .productUnitPrice(product.getUnitPrice())
                            .build();
                })
                .toList();

        // 4. 원부자재 정보 DTO 생성
        BomImplosionResponseDTO.MaterialInfo materialInfo = BomImplosionResponseDTO.MaterialInfo.builder()
                .id(rawMaterial.getId())
                .name(rawMaterial.getName())
                .materialCode(rawMaterial.getMaterialCode())
                .spec(rawMaterial.getSpec())
                .type(rawMaterial.getType())
                .baseUnit(rawMaterial.getBaseUnit())
                .unitPrice(rawMaterial.getUnitPrice())
                .build();

        // 5. 응답 DTO 생성
        return BomImplosionResponseDTO.builder()
                .rawMaterial(materialInfo)
                .usedInProducts(usedInProducts)
                .totalProductCount(usedInProducts.size())
                .build();
    }
}
