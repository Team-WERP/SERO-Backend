package com.werp.sero.order.command.application.service;

import com.werp.sero.common.file.S3Uploader;
import com.werp.sero.common.util.PdfGenerator;
import com.werp.sero.order.command.domain.aggregate.SalesOrder;
import com.werp.sero.order.command.domain.aggregate.SalesOrderItem;
import com.werp.sero.order.command.domain.repository.SOItemRepository;
import com.werp.sero.order.command.domain.repository.SORepository;
import com.werp.sero.order.exception.SalesOrderNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SOPdfService {
    private final SORepository soRepository;
    private final SOItemRepository soItemRepository;
    private final S3Uploader s3Uploader;

    @Transactional
    public String generateAndUpload(int orderId) {
        SalesOrder so = soRepository.findById(orderId)
                .orElseThrow(SalesOrderNotFoundException::new);

        List<SalesOrderItem> items = soItemRepository.findBySalesOrder_Id(orderId);

        String html = buildHtml(so, items);

        byte[] pdfBytes = PdfGenerator.generate(html);

        String fileName = so.getSoCode() + ".pdf";

        return s3Uploader.uploadBytes(
                "sero/documents/",
                pdfBytes,
                fileName,
                "application/pdf"
        );
    }

    private String buildHtml(SalesOrder order, List<SalesOrderItem> items) {
        DecimalFormat df = new DecimalFormat("#,###");
        long totalQuantity = items.stream().mapToLong(SalesOrderItem::getQuantity).sum();

        StringBuilder sb = new StringBuilder();

        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        sb.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">");
        sb.append("<html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"ko\" lang=\"ko\">");
        sb.append("<head>");
        sb.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />");

        sb.append("<style>");
        sb.append("@import url('https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@400;700&amp;display=swap');");
        sb.append("""
        body { font-family: 'Noto Sans KR', sans-serif; padding: 10mm; color: #000; line-height: 1.4; }
        .header-title { text-align: center; font-size: 32px; font-weight: bold; letter-spacing: 15px; border-bottom: 2px solid #000; padding-bottom: 10px; margin-bottom: 25px; }
        
        table { width: 100%; border-collapse: collapse; margin-bottom: 15px; table-layout: fixed; }
        th, td { border: 1px solid #000; padding: 8px; font-size: 11px; word-break: break-all; }
        .bg-gray { background-color: #f2f2f2; font-weight: bold; text-align: center; }
        
        .center { text-align: center; }
        .right { text-align: right; }
        .left { text-align: left; padding-left: 8px; }
        .bold { font-weight: bold; }
        
        .summary-table td { padding: 12px; font-size: 12px; }
    """);
        sb.append("</style></head><body>");

        sb.append("<h1 class=\"header-title\">주 문 서</h1>");

        sb.append("<table style=\"border:none; margin-bottom:20px;\"><tr>");
        sb.append("<td style=\"border:none; padding:0; vertical-align:top; width:49%;\">");

        String clientName = (order.getClient() != null) ? order.getClient().getCompanyName() : "-";
        String clientAddress = (order.getClient() != null && order.getClient().getAddress() != null) ? order.getClient().getAddress() : "-";

        sb.append("<table><tbody>");
        sb.append("<tr><th colspan=\"2\" class=\"bg-gray\" style=\"font-size:13px; padding:10px;\">발주처</th></tr>");
        sb.append("<tr><td class=\"bg-gray\" style=\"width:70px\">회사명</td><td>").append(clientName).append("</td></tr>");
        sb.append("<tr><td class=\"bg-gray\">담당자</td><td>").append(order.getRecipientName()).append("</td></tr>");
        sb.append("<tr><td class=\"bg-gray\">연락처</td><td>").append(order.getRecipientContact()).append("</td></tr>");
        sb.append("<tr><td class=\"bg-gray\">주소</td><td style=\"font-size:10px\">").append(clientAddress).append("</td></tr>");
        sb.append("</tbody></table></td>");

        sb.append("<td style=\"border:none; width:2%;\"></td>"); // 중앙 간격

        sb.append("<td style=\"border:none; padding:0; vertical-align:top; width:49%;\">");
        String managerName = (order.getEmployee() != null) ? order.getEmployee().getName() : "-";
        sb.append("<table><tbody>");
        sb.append("<tr><th colspan=\"2\" class=\"bg-gray\" style=\"font-size:13px; padding:10px;\">수주처</th></tr>");
        sb.append("<tr><td class=\"bg-gray\" style=\"width:70px\">회사명</td><td>새로</td></tr>");
        sb.append("<tr><td class=\"bg-gray\">담당자</td><td>").append(managerName).append("</td></tr>");
        sb.append("<tr><td class=\"bg-gray\">연락처</td><td>010-4444-5555</td></tr>");
        sb.append("<tr><td class=\"bg-gray\">주소</td><td style=\"font-size:10px\">서울시 동작구 보라매로 87</td></tr>");
        sb.append("</tbody></table></td></tr></table>");

        String orderedAt = (order.getOrderedAt() != null) ? order.getOrderedAt().toString().substring(0, 10) : "-";
        String shippedAt = (order.getShippedAt() != null) ? order.getShippedAt().toString().substring(0, 10) : "-";

        sb.append("<table><tbody>");
        sb.append("<tr><td class=\"bg-gray\" style=\"width:15%\">주문 번호</td><td class=\"center\">").append(order.getSoCode()).append("</td>");
        sb.append("<td class=\"bg-gray\" style=\"width:15%\">주문 일자</td><td class=\"center\">").append(orderedAt).append("</td></tr>");
        sb.append("<tr><td class=\"bg-gray\">납기 일자</td><td colspan=\"3\" class=\"left\">").append(shippedAt).append("</td></tr>");
        sb.append("<tr><td class=\"bg-gray\">비고</td><td colspan=\"3\" class=\"left\" style=\"font-size:10px\">").append(order.getNote() != null ? order.getNote() : "-").append("</td></tr>");
        sb.append("</tbody></table>");

        sb.append("<h3 style=\"font-size:14px; margin: 20px 0 10px 0;\">주문 품목 정보</h3>");
        sb.append("<table><thead><tr class=\"bg-gray\">");
        sb.append("<th style=\"width:30px\">No</th>");
        sb.append("<th style=\"width:70px\">품목 코드</th>");
        sb.append("<th>품목명</th>");
        sb.append("<th style=\"width:70px\">규격</th>");
        sb.append("<th style=\"width:45px\">수량</th>");
        sb.append("<th style=\"width:30px\">단위</th>");
        sb.append("<th style=\"width:70px\">단가(원)</th>");
        sb.append("<th style=\"width:90px\">금액</th>");
        sb.append("</tr></thead><tbody>");

        int idx = 1;
        for (SalesOrderItem item : items) {
            sb.append("<tr>")
                    .append("<td class=\"center\">").append(idx++).append("</td>")
                    .append("<td class=\"center\">").append(item.getItemCode()).append("</td>")
                    .append("<td class=\"left\">").append(item.getItemName()).append("</td>")
                    .append("<td class=\"center\">").append(item.getSpec() != null ? item.getSpec() : "-").append("</td>")
                    .append("<td class=\"center\">").append(df.format(item.getQuantity())).append("</td>")
                    .append("<td class=\"center\">").append(item.getUnit()).append("</td>")
                    .append("<td class=\"right\">").append(df.format(item.getUnitPrice())).append("</td>")
                    .append("<td class=\"right\">").append(df.format(item.getTotalPrice())).append("</td>")
                    .append("</tr>");
        }

        sb.append("<tr class=\"bg-gray\">")
                .append("<td colspan=\"4\">합 계</td>")
                .append("<td class=\"center\">").append(df.format(totalQuantity)).append("</td>")
                .append("<td colspan=\"2\"></td>")
                .append("<td class=\"right\">").append(df.format(order.getTotalPrice())).append("</td>")
                .append("</tr>");
        sb.append("</tbody></table>");
        
        sb.append("<table class=\"summary-table\" style=\"margin-top:30px;\"><tbody>");
        sb.append("<tr>");
        sb.append("<td class=\"bg-gray\" style=\"width:20%;\">총 수량</td>");
        sb.append("<td class=\"center\" style=\"width:30%; font-weight:bold;\">").append(df.format(totalQuantity)).append("</td>");
        sb.append("<td class=\"bg-gray\" style=\"width:20%;\">공급가액</td>");
        sb.append("<td class=\"right\" style=\"width:30%; font-weight:bold;\">").append(df.format(order.getTotalPrice())).append(" 원</td>");
        sb.append("</tr>");
        sb.append("<tr>");
        sb.append("<td class=\"bg-gray\" style=\"padding:20px;\">총 합계 금액</td>");
        sb.append("<td colspan=\"3\" class=\"right\" style=\"padding:20px; font-size:16px; font-weight:bold;\">")
                .append(df.format(order.getTotalPrice())).append(" 원</td>");
        sb.append("</tr></tbody></table>");

        sb.append("</body></html>");

        return sb.toString();
    }
}