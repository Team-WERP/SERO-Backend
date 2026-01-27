package com.werp.sero.file.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class PresignedUrlRequestDTO {
    @NotBlank
    private String fileName;

    @NotBlank
    @Schema(defaultValue = "image/png")
    private String contentType;
}