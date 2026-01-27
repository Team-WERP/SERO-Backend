package com.werp.sero.file.service;

import com.werp.sero.file.dto.PresignedResponseDTO;
import com.werp.sero.file.dto.PresignedUrlRequestDTO;

public interface FileService {
    PresignedResponseDTO generatePresignedUrl(final PresignedUrlRequestDTO requestDTO);
}