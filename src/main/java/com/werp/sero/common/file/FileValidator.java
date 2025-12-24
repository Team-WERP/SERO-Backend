package com.werp.sero.common.file;

import com.werp.sero.common.error.ErrorCode;
import com.werp.sero.common.error.exception.BusinessException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

@Component
public class FileValidator {
    private static final List<String> ALLOWED_IMAGE_MIME_TYPES =
            Arrays.asList("image/gif", "image/jpeg", "image/png", "image/webp");

    private static final List<String> ALLOWED_PDF_MIME_TYPES =
            List.of("application/pdf");

    private static final List<String> ALLOWED_ALL_MIME_TYPES;

    static {
        ALLOWED_ALL_MIME_TYPES = new java.util.ArrayList<>(ALLOWED_IMAGE_MIME_TYPES);
        ALLOWED_ALL_MIME_TYPES.addAll(ALLOWED_PDF_MIME_TYPES);
    }

    public void validateImage(final MultipartFile file) {
        validateFile(file, ALLOWED_IMAGE_MIME_TYPES);
    }

    public void validatePdf(final MultipartFile file) {
        validateFile(file, ALLOWED_PDF_MIME_TYPES);
    }

    public void validateImageOrPdf(final MultipartFile file) {
        validateFile(file, ALLOWED_ALL_MIME_TYPES);
    }

    private void validateFile(final MultipartFile file, final List<String> allowedMimeTypes) {
        if (file == null || file.isEmpty()) {
            throw new BusinessException(ErrorCode.FILE_NOT_FOUND);
        }

        final String contentType = file.getContentType();

        if (contentType == null || !allowedMimeTypes.contains(contentType)) {
            throw new BusinessException(ErrorCode.FILE_INVALID_EXTENSION);
        }
    }
}
