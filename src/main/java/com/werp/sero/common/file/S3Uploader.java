package com.werp.sero.common.file;

import com.werp.sero.common.error.ErrorCode;
import com.werp.sero.common.error.exception.BusinessException;
import com.werp.sero.common.error.exception.SystemException;
import io.awspring.cloud.s3.S3Exception;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class S3Uploader {
    private final S3Client s3Client;

    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${spring.cloud.aws.region.static}")
    private String region;

    public String upload(final String objectPath, final MultipartFile file) {
        try {
            final String objectKey = generateKey(objectPath, file);

            final PutObjectRequest putRequest = PutObjectRequest.builder()
                    .bucket(bucket)
                    .key(objectKey)
                    .contentType(file.getContentType())
                    .build();

            s3Client.putObject(putRequest, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

            return generateS3Url(objectKey);
        } catch (S3Exception | IOException e) {
            throw new SystemException(ErrorCode.S3_UPLOAD_FAILED);
        }
    }

    public void delete(final String s3Url) {
        try {
            final String objectKey = extractKey(s3Url);

            final DeleteObjectRequest deleteRequest = DeleteObjectRequest.builder()
                    .bucket(bucket)
                    .key(objectKey)
                    .build();

            s3Client.deleteObject(deleteRequest);
        } catch (Exception e) {
            throw new SystemException(ErrorCode.S3_DELETE_ERROR);
        }
    }

    private String generateKey(final String path, final MultipartFile file) {
        final String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());
        final String fileName = UUID.randomUUID() + "." + extension;

        return path + fileName;
    }

    private String extractKey(final String s3Url) {
        try {
            final URI uri = new URI(s3Url);

            return uri.getPath().substring(1);
        } catch (URISyntaxException e) {
            throw new BusinessException(ErrorCode.S3_URL_INVALID);
        }
    }

    private String generateS3Url(final String objectKey) {
        return "https://" + bucket + ".s3." + region + ".amazonaws.com/" + objectKey;
    }
}
