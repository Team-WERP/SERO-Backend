package com.werp.sero.material.controller;

import com.werp.sero.common.security.AccessType;
import com.werp.sero.common.security.RequirePermission;
import com.werp.sero.material.dto.MaterialCreateRequestDTO;
import com.werp.sero.material.dto.MaterialDetailResponseDTO;
import com.werp.sero.material.dto.MaterialListResponseDTO;
import com.werp.sero.material.dto.MaterialUpdateRequestDTO;
import com.werp.sero.material.service.MaterialService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/materials")
@RequiredArgsConstructor
public class MaterialController {

    private final MaterialService materialService;

    @GetMapping
    @RequirePermission(menu = "MM_MAT", authorities = {"AC_SYS", "AC_SAL", "AC_PRO", "AC_WHS"}, accessType = AccessType.READ)
    public List<MaterialListResponseDTO> getMaterials(
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String keyword) {

        return materialService.getMaterialList(type, status, keyword);
    }

    @GetMapping("/{id}")
    @RequirePermission(menu = "MM_MAT", authorities = {"AC_SYS", "AC_SAL", "AC_PRO", "AC_WHS"}, accessType = AccessType.READ)
    public MaterialDetailResponseDTO getMaterial(@PathVariable int id) {
        return materialService.getMaterialDetail(id);
    }

    @PostMapping
    @RequirePermission(menu = "MM_MAT", authorities = {"AC_SYS", "AC_SAL", "AC_PRO"}, accessType = AccessType.WRITE)
    public void createMaterial(@RequestBody MaterialCreateRequestDTO request) {
        materialService.createMaterial(request, 5); // 임시 로그인 유저(생산팀)
    }

    @PutMapping("/{id}")
    @RequirePermission(menu = "MM_MAT", authorities = {"AC_SYS", "AC_SAL", "AC_PRO"}, accessType = AccessType.WRITE)
    public void updateMaterial(
            @PathVariable int id,
            @RequestBody MaterialUpdateRequestDTO request) {

        materialService.updateMaterial(id, request);
    }

    @PatchMapping("/{id}/deactivate")
    @RequirePermission(menu = "MM_MAT", authorities = {"AC_SYS", "AC_SAL", "AC_PRO"}, accessType = AccessType.WRITE)
    public void deactivate(@PathVariable int id) {
        materialService.deactivateMaterial(id);
    }
}
