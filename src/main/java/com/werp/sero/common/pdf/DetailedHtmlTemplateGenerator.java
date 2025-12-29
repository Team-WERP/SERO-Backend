package com.werp.sero.common.pdf;

import com.werp.sero.shipping.query.dto.DODetailResponseDTO;
import com.werp.sero.shipping.query.dto.GIDetailResponseDTO;
import org.springframework.stereotype.Component;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * 프론트엔드 미리보기와 동일한 상세 PDF 템플릿 생성 클래스
 */
@Component
public class DetailedHtmlTemplateGenerator {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final NumberFormat NUMBER_FORMAT = NumberFormat.getInstance(Locale.KOREA);

    /**
     * 납품서 상세 HTML 생성 (프론트엔드 양식과 동일)
     */
    public String generateDeliveryOrderDetailHtml(DODetailResponseDTO dto) {
        StringBuilder html = new StringBuilder();

        html.append("<!DOCTYPE html>");
        html.append("<html>");
        html.append("<head>");
        html.append("<meta charset='UTF-8'/>");
        html.append("<title>납품서</title>");
        html.append("<style>");
        html.append(getDeliveryOrderStyles());
        html.append("</style>");
        html.append("</head>");
        html.append("<body>");
        html.append("<div class='delivery-order-content'>");

        // 헤더
        html.append("<div class='do-header'>");
        html.append("<h1>납품서</h1>");
        html.append("</div>");

        // 기본 정보 테이블
        html.append("<table class='info-table'>");
        html.append("<tr>");
        html.append("<th class='col-label'>문서번호</th>");
        html.append("<td class='col-value'>").append(dto.getDoCode()).append("</td>");
        html.append("<th class='col-label'>일자</th>");
        html.append("<td class='col-value'>").append(formatDate(dto.getCreatedAt())).append("</td>");
        html.append("</tr>");
        html.append("<tr>");
        html.append("<th class='col-label'>수신처</th>");
        html.append("<td colspan='3' class='col-value'>").append(dto.getRecipient()).append("</td>");
        html.append("</tr>");
        html.append("<tr>");
        html.append("<th class='col-label'>상호</th>");
        html.append("<td class='col-value'>").append(dto.getCompanyName()).append("</td>");
        html.append("<th class='col-label'>대표자</th>");
        html.append("<td class='col-value'>").append(dto.getCeoName()).append("</td>");
        html.append("</tr>");
        html.append("<tr>");
        html.append("<th class='col-label'>사업자번호</th>");
        html.append("<td class='col-value'>").append(dto.getBusinessNo()).append("</td>");
        html.append("<th class='col-label'>전화번호</th>");
        html.append("<td class='col-value'>").append(dto.getCompanyContact()).append("</td>");
        html.append("</tr>");
        html.append("<tr>");
        html.append("<th class='col-label'>주소</th>");
        html.append("<td colspan='3' class='col-value'>").append(dto.getAddress()).append("</td>");
        html.append("</tr>");
        html.append("<tr>");
        html.append("<th class='col-label'>업태</th>");
        html.append("<td class='col-value'>").append(dto.getBusinessType()).append("</td>");
        html.append("<th class='col-label'>업종</th>");
        html.append("<td class='col-value'>").append(dto.getBusinessItem()).append("</td>");
        html.append("</tr>");
        html.append("<tr>");
        html.append("<th class='col-label'>합계금액</th>");
        html.append("<td colspan='3' class='col-value total-amount'>");
        html.append("일금 ").append(numberToKorean(dto.getTotalAmount()));
        html.append(" (₩").append(formatNumber(dto.getTotalAmount())).append(")");
        html.append("</td>");
        html.append("</tr>");
        html.append("</table>");

        // 품목 테이블
        html.append("<table class='items-table'>");
        html.append("<thead>");
        html.append("<tr>");
        html.append("<th>No.</th>");
        html.append("<th>품명</th>");
        html.append("<th>규격</th>");
        html.append("<th>수량</th>");
        html.append("<th>단가</th>");
        html.append("<th>금액</th>");
        html.append("</tr>");
        html.append("</thead>");
        html.append("<tbody>");

        // 품목 리스트
        int index = 1;
        for (DODetailResponseDTO.DODetailItemDTO item : dto.getItems()) {
            html.append("<tr>");
            html.append("<td>").append(index++).append("</td>");
            html.append("<td>").append(item.getItemName()).append("</td>");
            html.append("<td>").append(item.getSpec() != null ? item.getSpec() : "-").append("</td>");
            html.append("<td>").append(formatNumber(item.getQuantity())).append("</td>");
            html.append("<td>").append(formatNumber(item.getUnitPrice())).append("</td>");
            html.append("<td>").append(formatNumber(item.getAmount())).append("</td>");
            html.append("</tr>");
        }

        // 빈 행 추가 (최소 10행 유지)
        int emptyRows = Math.max(0, 10 - dto.getItems().size());
        for (int i = 0; i < emptyRows; i++) {
            html.append("<tr>");
            html.append("<td>&nbsp;</td>");
            html.append("<td>&nbsp;</td>");
            html.append("<td>&nbsp;</td>");
            html.append("<td>&nbsp;</td>");
            html.append("<td>&nbsp;</td>");
            html.append("<td>&nbsp;</td>");
            html.append("</tr>");
        }

        html.append("</tbody>");
        html.append("<tfoot>");
        html.append("<tr>");
        html.append("<td colspan='5' class='footer-label'>합계</td>");
        html.append("<td class='footer-total'>").append(formatNumber(dto.getTotalAmount())).append("</td>");
        html.append("</tr>");
        html.append("</tfoot>");
        html.append("</table>");

        // 하단 정보
        html.append("<table class='bottom-info-table'>");
        html.append("<tr>");
        html.append("<th class='col-label'>납품기한</th>");
        html.append("<td class='col-value'>").append(formatDate(dto.getShippedAt())).append("</td>");
        html.append("</tr>");
        html.append("<tr>");
        html.append("<th class='col-label'>납품장소</th>");
        html.append("<td class='col-value'>").append(dto.getDeliveryLocation()).append("</td>");
        html.append("</tr>");
        html.append("<tr>");
        html.append("<th class='col-label'>특이사항</th>");
        html.append("<td class='col-value'>").append(dto.getNote() != null ? dto.getNote() : "-").append("</td>");
        html.append("</tr>");
        html.append("</table>");

        html.append("</div>");
        html.append("</body>");
        html.append("</html>");

        return html.toString();
    }

    /**
     * 출고지시서 상세 HTML 생성 (프론트엔드 양식과 동일)
     */
    public String generateGoodsIssueDetailHtml(GIDetailResponseDTO dto) {
        StringBuilder html = new StringBuilder();

        html.append("<!DOCTYPE html>");
        html.append("<html>");
        html.append("<head>");
        html.append("<meta charset='UTF-8'/>");
        html.append("<title>출고지시서</title>");
        html.append("<style>");
        html.append(getGoodsIssueStyles());
        html.append("</style>");
        html.append("</head>");
        html.append("<body>");
        html.append("<div class='goods-issue-content'>");

        // 헤더 (좌: 제목, 우: 문서정보)
        html.append("<div class='gi-header'>");
        html.append("<h1>출고지시서</h1>");
        html.append("<table class='header-info'>");
        html.append("<tbody>");
        html.append("<tr>");
        html.append("<th>문서번호</th>");
        html.append("<td>").append(dto.getGiCode()).append("</td>");
        html.append("</tr>");
        html.append("<tr>");
        html.append("<th>출고예정일자</th>");
        html.append("<td>").append(formatDate(dto.getScheduledAt())).append("</td>");
        html.append("</tr>");
        html.append("<tr>");
        html.append("<th>참조문서</th>");
        html.append("<td>").append(dto.getDoCode()).append("</td>");
        html.append("</tr>");
        html.append("</tbody>");
        html.append("</table>");
        html.append("</div>");

        html.append("<hr class='divider' />");

        // 출고/배송 정보
        html.append("<div class='info-section'>");

        // FROM 섹션
        html.append("<div class='from-section'>");
        html.append("<h3>출고 정보 (From)</h3>");
        html.append("<table class='detail-table'>");
        html.append("<tbody>");
        html.append("<tr>");
        html.append("<th>출고 창고</th>");
        html.append("<td>").append(dto.getWarehouseName()).append("</td>");
        html.append("</tr>");
        html.append("<tr>");
        html.append("<th>담당자</th>");
        html.append("<td>").append(dto.getManagerName() != null ? dto.getManagerName() : "미배정").append("</td>");
        html.append("</tr>");
        html.append("</tbody>");
        html.append("</table>");
        html.append("</div>");

        // TO 섹션
        html.append("<div class='to-section'>");
        html.append("<h3>배송 정보 (To)</h3>");
        html.append("<table class='detail-table'>");
        html.append("<tbody>");
        html.append("<tr>");
        html.append("<th>납품처</th>");
        html.append("<td>").append(dto.getCompanyName()).append("</td>");
        html.append("</tr>");
        html.append("<tr>");
        html.append("<th>도착지 주소</th>");
        html.append("<td>").append(dto.getAddress()).append("</td>");
        html.append("</tr>");
        html.append("<tr>");
        html.append("<th>연락처</th>");
        html.append("<td>").append(dto.getRecipientContact()).append("</td>");
        html.append("</tr>");
        html.append("</tbody>");
        html.append("</table>");
        html.append("<div class='recipient-info'>");
        html.append("<span class='label'>수령인 이름</span>");
        html.append("<span class='value'>").append(dto.getRecipientName()).append("</span>");
        html.append("</div>");
        html.append("</div>");

        html.append("</div>");

        // 출고 품목 상세
        html.append("<h3>출고 품목 상세</h3>");
        html.append("<table class='items-table'>");
        html.append("<thead>");
        html.append("<tr>");
        html.append("<th>No</th>");
        html.append("<th>품목코드</th>");
        html.append("<th>품목명</th>");
        html.append("<th>규격</th>");
        html.append("<th>단위</th>");
        html.append("<th>지시수량</th>");
        html.append("</tr>");
        html.append("</thead>");
        html.append("<tbody>");

        int index = 1;
        int totalQuantity = 0;
        for (GIDetailResponseDTO.GIDetailItemDTO item : dto.getItems()) {
            html.append("<tr>");
            html.append("<td>").append(index++).append("</td>");
            html.append("<td>").append(item.getItemCode()).append("</td>");
            html.append("<td>").append(item.getItemName()).append("</td>");
            html.append("<td>").append(item.getSpec() != null ? item.getSpec() : "-").append("</td>");
            html.append("<td>").append(item.getUnit()).append("</td>");
            html.append("<td>").append(formatNumber(item.getQuantityAUn())).append("</td>");
            html.append("</tr>");
            totalQuantity += item.getQuantityAUn();
        }

        // 빈 행 추가 (최소 5행 유지)
        int emptyRows = Math.max(0, 5 - dto.getItems().size());
        for (int i = 0; i < emptyRows; i++) {
            html.append("<tr>");
            html.append("<td>&nbsp;</td>");
            html.append("<td>&nbsp;</td>");
            html.append("<td>&nbsp;</td>");
            html.append("<td>&nbsp;</td>");
            html.append("<td>&nbsp;</td>");
            html.append("<td>&nbsp;</td>");
            html.append("</tr>");
        }

        html.append("</tbody>");
        html.append("<tfoot>");
        html.append("<tr>");
        html.append("<td colspan='5' class='footer-label'>합계 (Total)</td>");
        html.append("<td class='footer-total'>").append(formatNumber(totalQuantity)).append("</td>");
        html.append("</tr>");
        html.append("</tfoot>");
        html.append("</table>");

        // 특이사항
        html.append("<h3>특이사항 (Note)</h3>");
        html.append("<div class='note-box'>");
        html.append(dto.getNote() != null && !dto.getNote().isEmpty() ? dto.getNote() : "-");
        html.append("</div>");

        html.append("</div>");
        html.append("</body>");
        html.append("</html>");

        return html.toString();
    }

    /**
     * 납품서 CSS 스타일 (프론트엔드와 동일)
     */
    private String getDeliveryOrderStyles() {
        return """
            * {
                margin: 0;
                padding: 0;
                box-sizing: border-box;
            }
            body {
                font-family: 'Malgun Gothic', 'Apple SD Gothic Neo', sans-serif;
                padding: 40px;
                background: white;
            }
            .delivery-order-content {
                max-width: 210mm;
                margin: 0 auto;
            }
            .do-header {
                text-align: center;
                margin-bottom: 30px;
                border-bottom: 3px solid #333;
                padding-bottom: 15px;
            }
            .do-header h1 {
                font-size: 32px;
                font-weight: bold;
                color: #333;
            }
            .info-table {
                width: 100%;
                border-collapse: collapse;
                margin-bottom: 20px;
                border: 2px solid #333;
            }
            .info-table th, .info-table td {
                border: 1px solid #333;
                padding: 8px 12px;
                font-size: 13px;
            }
            .info-table th {
                background-color: #f5f5f5;
                font-weight: bold;
                text-align: center;
                width: 15%;
            }
            .info-table td {
                text-align: left;
            }
            .info-table .total-amount {
                font-weight: bold;
                font-size: 14px;
            }
            .items-table {
                width: 100%;
                border-collapse: collapse;
                margin-bottom: 20px;
                border: 2px solid #333;
            }
            .items-table thead {
                background-color: #f0f0f0;
            }
            .items-table th, .items-table td {
                border: 1px solid #333;
                padding: 8px;
                text-align: center;
                font-size: 12px;
            }
            .items-table tbody tr {
                height: 30px;
            }
            .items-table tfoot .footer-label {
                font-weight: bold;
                background-color: #f5f5f5;
            }
            .items-table tfoot .footer-total {
                font-weight: bold;
                font-size: 14px;
            }
            .bottom-info-table {
                width: 100%;
                border-collapse: collapse;
                border: 2px solid #333;
            }
            .bottom-info-table th, .bottom-info-table td {
                border: 1px solid #333;
                padding: 8px 12px;
                font-size: 13px;
            }
            .bottom-info-table th {
                background-color: #f5f5f5;
                font-weight: bold;
                text-align: center;
                width: 20%;
            }
            .bottom-info-table td {
                text-align: left;
            }
            """;
    }

    /**
     * 출고지시서 CSS 스타일 (프론트엔드와 동일)
     */
    private String getGoodsIssueStyles() {
        return """
            * {
                margin: 0;
                padding: 0;
                box-sizing: border-box;
            }
            body {
                font-family: 'Malgun Gothic', sans-serif;
                padding: 20px;
            }
            .goods-issue-content {
                max-width: 210mm;
                margin: 0 auto;
            }
            .gi-header {
                position: relative;
                margin-bottom: 20px;
            }
            .gi-header h1 {
                font-size: 32px;
                font-weight: bold;
                color: #111827;
                display: inline-block;
            }
            .header-info {
                float: right;
                border-collapse: collapse;
                text-align: left;
            }
            .header-info th {
                padding: 4px 12px;
                font-size: 14px;
                font-weight: 600;
                color: #374151;
                background: #f3f4f6;
                border: 1px solid #d1d5db;
            }
            .header-info td {
                padding: 4px 12px;
                font-size: 14px;
                color: #111827;
                border: 1px solid #d1d5db;
            }
            .divider {
                border: none;
                border-top: 2px solid #111827;
                margin: 20px 0;
                clear: both;
            }
            .info-section {
                display: flex;
                gap: 20px;
                margin-bottom: 30px;
            }
            .from-section, .to-section {
                flex: 1;
            }
            .info-section h3 {
                font-size: 16px;
                font-weight: bold;
                color: #111827;
                margin-bottom: 10px;
                padding-bottom: 5px;
                border-bottom: 2px solid #e5e7eb;
            }
            .detail-table {
                width: 100%;
                border-collapse: collapse;
                margin-bottom: 10px;
            }
            .detail-table th {
                width: 100px;
                padding: 8px 12px;
                font-size: 13px;
                font-weight: 600;
                color: #374151;
                background: #f9fafb;
                border: 1px solid #e5e7eb;
                text-align: left;
            }
            .detail-table td {
                padding: 8px 12px;
                font-size: 13px;
                color: #111827;
                border: 1px solid #e5e7eb;
            }
            .recipient-info {
                padding: 10px;
                background: #f9fafb;
                border: 1px solid #e5e7eb;
                border-radius: 4px;
            }
            .recipient-info .label {
                font-size: 13px;
                font-weight: 600;
                color: #374151;
                margin-right: 30px;
            }
            .recipient-info .value {
                font-size: 14px;
                font-weight: bold;
                color: #111827;
            }
            h3 {
                font-size: 16px;
                font-weight: bold;
                color: #111827;
                margin: 20px 0 10px 0;
                padding-bottom: 5px;
                border-bottom: 2px solid #e5e7eb;
            }
            .items-table {
                width: 100%;
                border-collapse: collapse;
                margin-bottom: 20px;
                border: 1px solid #d1d5db;
            }
            .items-table thead {
                background: #f3f4f6;
            }
            .items-table th {
                padding: 10px 8px;
                font-size: 13px;
                font-weight: 600;
                color: #374151;
                border: 1px solid #d1d5db;
                text-align: center;
            }
            .items-table td {
                padding: 8px;
                font-size: 13px;
                color: #111827;
                border: 1px solid #e5e7eb;
                text-align: center;
            }
            .items-table tfoot {
                background: #f9fafb;
                font-weight: 600;
            }
            .items-table tfoot td {
                padding: 10px 8px;
                border-top: 2px solid #d1d5db;
            }
            .footer-label {
                text-align: right;
                font-weight: bold;
            }
            .footer-total {
                font-weight: bold;
                color: #111827;
            }
            .note-box {
                padding: 15px;
                background: #f9fafb;
                border: 1px solid #e5e7eb;
                border-radius: 4px;
                min-height: 60px;
                font-size: 13px;
                color: #374151;
                white-space: pre-wrap;
            }
            """;
    }

    // 유틸리티 메서드들

    private String formatDate(String dateTime) {
        if (dateTime == null || dateTime.isEmpty()) return "-";
        try {
            if (dateTime.contains("T")) {
                LocalDateTime ldt = LocalDateTime.parse(dateTime);
                return ldt.format(DATE_FORMATTER);
            } else if (dateTime.contains(" ")) {
                LocalDateTime ldt = LocalDateTime.parse(dateTime.replace(" ", "T"));
                return ldt.format(DATE_FORMATTER);
            }
            return dateTime;
        } catch (Exception e) {
            return dateTime;
        }
    }

    private String formatNumber(long number) {
        return NUMBER_FORMAT.format(number);
    }

    private String formatNumber(int number) {
        return NUMBER_FORMAT.format(number);
    }

    /**
     * 숫자를 한글로 변환
     */
    private String numberToKorean(long number) {
        if (number == 0) return "영원";

        String[] units = {"", "만", "억", "조"};
        String[] nums = {"", "일", "이", "삼", "사", "오", "육", "칠", "팔", "구"};

        StringBuilder result = new StringBuilder();
        int unitIndex = 0;

        while (number > 0) {
            int part = (int) (number % 10000);
            if (part > 0) {
                StringBuilder partStr = new StringBuilder();

                int thousand = part / 1000;
                int hundred = (part % 1000) / 100;
                int ten = (part % 100) / 10;
                int one = part % 10;

                if (thousand > 0) partStr.append(nums[thousand]).append("천");
                if (hundred > 0) partStr.append(nums[hundred]).append("백");
                if (ten > 0) partStr.append(nums[ten]).append("십");
                if (one > 0) partStr.append(nums[one]);

                partStr.append(units[unitIndex]);
                result.insert(0, partStr);
            }

            number /= 10000;
            unitIndex++;
        }

        return result.append("원정").toString();
    }
}
