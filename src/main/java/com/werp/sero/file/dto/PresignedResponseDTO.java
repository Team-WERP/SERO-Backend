package com.werp.sero.file.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PresignedResponseDTO {
    private String presignedUrl;
    private String s3Url;
}