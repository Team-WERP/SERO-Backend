package com.werp.sero.file.service;

import com.werp.sero.common.file.S3Uploader;
import com.werp.sero.file.dto.PresignedResponseDTO;
import com.werp.sero.file.dto.PresignedUrlRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FileServiceImpl implements FileService {
    private final S3Uploader s3Uploader;

    @Override
    public PresignedResponseDTO generatePresignedUrl(final PresignedUrlRequestDTO requestDTO) {
        return s3Uploader.createPresignedPutUrl("sero/temp/", requestDTO.getFileName(), requestDTO.getContentType());
    }
}