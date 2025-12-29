package com.werp.sero.common.pdf;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.resolver.font.DefaultFontProvider;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.layout.font.FontProvider;
import com.werp.sero.common.error.ErrorCode;
import com.werp.sero.common.error.exception.SystemException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;

/**
 * HTML을 PDF로 변환하는 유틸리티 클래스
 * iText7의 html2pdf 라이브러리 사용
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class PdfGenerator {

    /**
     * HTML 문자열을 PDF byte 배열로 변환
     *
     * @param htmlContent HTML 문자열
     * @return PDF byte 배열
     */
    public byte[] generatePdfFromHtml(final String htmlContent) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            // ConverterProperties 설정
            ConverterProperties converterProperties = new ConverterProperties();
            converterProperties.setBaseUri("");

            // 한글 폰트 지원을 위한 FontProvider 설정
            FontProvider fontProvider = new DefaultFontProvider(true, true, true);

            // iText의 내장 한글 폰트 추가 (HYSMyeongJo, HYGoThic 등)
            try {
                PdfFont koreanFont = PdfFontFactory.createFont("HYSMyeongJo-Medium", "UniKS-UCS2-H");
                fontProvider.addFont(koreanFont.getFontProgram(), PdfEncodings.IDENTITY_H);
            } catch (Exception e) {
                log.warn("한글 폰트 로드 실패, 기본 폰트 사용: {}", e.getMessage());
                // 기본 폰트로 진행
            }

            converterProperties.setFontProvider(fontProvider);

            // HTML → PDF 변환
            HtmlConverter.convertToPdf(htmlContent, outputStream, converterProperties);

            log.info("PDF 생성 완료 - 크기: {} bytes", outputStream.size());

            return outputStream.toByteArray();

        } catch (Exception e) {
            log.error("PDF 생성 실패", e);
            throw new SystemException(ErrorCode.PDF_GENERATION_FAILED);
        }
    }
}
