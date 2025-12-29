package com.werp.sero.common.pdf;

import com.werp.sero.shipping.command.domain.aggregate.DeliveryOrder;
import com.werp.sero.shipping.command.domain.aggregate.GoodsIssue;
import org.springframework.stereotype.Component;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * PDF 생성을 위한 HTML 템플릿 생성 클래스
 */
@Component
public class HtmlTemplateGenerator {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분");
    private static final NumberFormat NUMBER_FORMAT = NumberFormat.getInstance(Locale.KOREA);

    /**
     * 납품서 HTML 생성
     */
    public String generateDeliveryOrderHtml(DeliveryOrder deliveryOrder) {
        StringBuilder html = new StringBuilder();

        html.append("<!DOCTYPE html>");
        html.append("<html>");
        html.append("<head>");
        html.append("<meta charset='UTF-8'/>");
        html.append("<title>납품서</title>");
        html.append("<style>");
        html.append(getCommonStyles());
        html.append("</style>");
        html.append("</head>");
        html.append("<body>");

        // 헤더
        html.append("<div class='header'>");
        html.append("<h1>납 품 서</h1>");
        html.append("<div class='document-number'>문서번호: ").append(deliveryOrder.getDoCode()).append("</div>");
        html.append("</div>");

        // 기본 정보
        html.append("<div class='section'>");
        html.append("<h2>기본 정보</h2>");
        html.append("<table>");
        html.append("<tr><td class='label'>작성일</td><td>").append(formatDateTime(deliveryOrder.getCreatedAt())).append("</td></tr>");
        html.append("<tr><td class='label'>납품일</td><td>").append(formatDateTime(deliveryOrder.getShippedAt())).append("</td></tr>");
        html.append("<tr><td class='label'>담당자</td><td>").append(deliveryOrder.getManager().getName()).append("</td></tr>");
        html.append("</table>");
        html.append("</div>");

        // 고객사 정보
        html.append("<div class='section'>");
        html.append("<h2>고객사 정보</h2>");
        html.append("<table>");
        html.append("<tr><td class='label'>회사명</td><td>").append(deliveryOrder.getSalesOrder().getClient().getCompanyName()).append("</td></tr>");
        html.append("<tr><td class='label'>대표자</td><td>").append(deliveryOrder.getSalesOrder().getClient().getCeoName()).append("</td></tr>");
        html.append("<tr><td class='label'>사업자번호</td><td>").append(deliveryOrder.getSalesOrder().getClient().getBusinessNo()).append("</td></tr>");
        html.append("</table>");
        html.append("</div>");

        // 주문 요약 정보
        html.append("<div class='section'>");
        html.append("<h2>주문 요약</h2>");
        html.append("<table>");
        html.append("<tr><td class='label'>주문번호</td><td>").append(deliveryOrder.getSalesOrder().getSoCode()).append("</td></tr>");
        html.append("<tr><td class='label'>대표품목</td><td>").append(deliveryOrder.getSalesOrder().getMainItemName()).append("</td></tr>");
        html.append("<tr><td class='label'>총 품목 수</td><td>").append(deliveryOrder.getSalesOrder().getTotalItemCount()).append("건</td></tr>");
        html.append("<tr><td class='label'>총 수량</td><td>").append(formatNumber(deliveryOrder.getSalesOrder().getTotalQuantity())).append("</td></tr>");
        html.append("<tr><td class='label'>총 금액</td><td>").append(formatNumber((int)deliveryOrder.getSalesOrder().getTotalPrice())).append("원</td></tr>");
        html.append("</table>");
        html.append("</div>");

        // 특이사항
        if (deliveryOrder.getNote() != null && !deliveryOrder.getNote().isEmpty()) {
            html.append("<div class='section'>");
            html.append("<h2>특이사항</h2>");
            html.append("<div class='note'>").append(deliveryOrder.getNote()).append("</div>");
            html.append("</div>");
        }

        html.append("</body>");
        html.append("</html>");

        return html.toString();
    }

    /**
     * 출고지시서 HTML 생성
     */
    public String generateGoodsIssueHtml(GoodsIssue goodsIssue) {
        StringBuilder html = new StringBuilder();

        html.append("<!DOCTYPE html>");
        html.append("<html>");
        html.append("<head>");
        html.append("<meta charset='UTF-8'/>");
        html.append("<title>출고지시서</title>");
        html.append("<style>");
        html.append(getCommonStyles());
        html.append("</style>");
        html.append("</head>");
        html.append("<body>");

        // 헤더
        html.append("<div class='header'>");
        html.append("<h1>출 고 지 시 서</h1>");
        html.append("<div class='document-number'>문서번호: ").append(goodsIssue.getGiCode()).append("</div>");
        html.append("</div>");

        // 기본 정보
        html.append("<div class='section'>");
        html.append("<h2>기본 정보</h2>");
        html.append("<table>");
        html.append("<tr><td class='label'>작성일</td><td>").append(formatDateTime(goodsIssue.getCreatedAt())).append("</td></tr>");
        html.append("<tr><td class='label'>지시자</td><td>").append(goodsIssue.getDrafter().getName()).append("</td></tr>");
        if (goodsIssue.getManager() != null) {
            html.append("<tr><td class='label'>담당자</td><td>").append(goodsIssue.getManager().getName()).append("</td></tr>");
        }
        html.append("<tr><td class='label'>창고</td><td>").append(goodsIssue.getWarehouse().getName()).append("</td></tr>");
        html.append("</table>");
        html.append("</div>");

        // 주문 정보
        html.append("<div class='section'>");
        html.append("<h2>관련 주문 정보</h2>");
        html.append("<table>");
        html.append("<tr><td class='label'>주문번호</td><td>").append(goodsIssue.getSalesOrder().getSoCode()).append("</td></tr>");
        html.append("<tr><td class='label'>고객사</td><td>").append(goodsIssue.getSalesOrder().getClient().getCompanyName()).append("</td></tr>");
        html.append("<tr><td class='label'>납품서번호</td><td>").append(goodsIssue.getDoCode()).append("</td></tr>");
        html.append("</table>");
        html.append("</div>");

        // 출고 요약 정보
        html.append("<div class='section'>");
        html.append("<h2>출고 요약</h2>");
        html.append("<table>");
        html.append("<tr><td class='label'>대표품목</td><td>").append(goodsIssue.getSalesOrder().getMainItemName()).append("</td></tr>");
        html.append("<tr><td class='label'>총 품목 수</td><td>").append(goodsIssue.getSalesOrder().getTotalItemCount()).append("건</td></tr>");
        html.append("<tr><td class='label'>총 출고 수량</td><td>").append(formatNumber(goodsIssue.getSalesOrder().getTotalQuantity())).append("</td></tr>");
        html.append("</table>");
        html.append("</div>");

        // 특이사항
        if (goodsIssue.getNote() != null && !goodsIssue.getNote().isEmpty()) {
            html.append("<div class='section'>");
            html.append("<h2>특이사항</h2>");
            html.append("<div class='note'>").append(goodsIssue.getNote()).append("</div>");
            html.append("</div>");
        }

        html.append("</body>");
        html.append("</html>");

        return html.toString();
    }

    /**
     * 공통 CSS 스타일
     */
    private String getCommonStyles() {
        return """
            body {
                font-family: 'Malgun Gothic', sans-serif;
                margin: 40px;
                color: #333;
            }
            .header {
                text-align: center;
                margin-bottom: 30px;
                border-bottom: 3px solid #4C4CDD;
                padding-bottom: 20px;
            }
            h1 {
                margin: 0;
                font-size: 32px;
                color: #4C4CDD;
            }
            .document-number {
                margin-top: 10px;
                font-size: 14px;
                color: #666;
            }
            .section {
                margin-bottom: 30px;
            }
            h2 {
                font-size: 18px;
                color: #4C4CDD;
                border-left: 4px solid #4C4CDD;
                padding-left: 10px;
                margin-bottom: 15px;
            }
            table {
                width: 100%;
                border-collapse: collapse;
                margin-bottom: 15px;
            }
            td, th {
                padding: 10px;
                border: 1px solid #ddd;
            }
            .label {
                background-color: #f5f5f5;
                font-weight: bold;
                width: 150px;
            }
            .items-table thead {
                background-color: #4C4CDD;
                color: white;
            }
            .items-table tbody tr:nth-child(even) {
                background-color: #f9f9f9;
            }
            .items-table td {
                text-align: center;
            }
            .note {
                padding: 15px;
                background-color: #f9f9f9;
                border: 1px solid #ddd;
                border-radius: 4px;
                min-height: 50px;
            }
            """;
    }

    private String formatDateTime(String dateTime) {
        if (dateTime == null || dateTime.isEmpty()) {
            return "-";
        }
        try {
            LocalDateTime ldt = LocalDateTime.parse(dateTime.replace(" ", "T"));
            return ldt.format(DATE_FORMATTER);
        } catch (Exception e) {
            return dateTime;
        }
    }

    private String formatNumber(int number) {
        return NUMBER_FORMAT.format(number);
    }
}
