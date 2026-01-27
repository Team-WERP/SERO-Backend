package com.werp.sero.file.controller;

import com.werp.sero.file.dto.PresignedResponseDTO;
import com.werp.sero.file.dto.PresignedUrlRequestDTO;
import com.werp.sero.file.service.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "파일", description = "파일 관련 API")
@RequestMapping("/files")
@RequiredArgsConstructor
@RestController
public class FileController {
    private final FileService fileService;

    @Operation(summary = "presigend url 발급")
    @PostMapping("/presigned-url")
    public ResponseEntity<PresignedResponseDTO> generatePresignedUrl(
            @Valid @RequestBody final PresignedUrlRequestDTO requestDTO) {
        final PresignedResponseDTO responseDTO = fileService.generatePresignedUrl(requestDTO);

        return ResponseEntity.ok(responseDTO);
    }
}