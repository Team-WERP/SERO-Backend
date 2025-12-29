package com.werp.sero.production.command.application.service;

import com.werp.sero.common.file.S3Uploader;
import com.werp.sero.common.util.PdfGenerator;
import com.werp.sero.production.command.domain.aggregate.ProductionRequest;
import com.werp.sero.production.command.domain.aggregate.ProductionRequestItem;
import com.werp.sero.production.command.domain.repository.PRItemRepository;
import com.werp.sero.production.command.domain.repository.PRRepository;
import com.werp.sero.production.exception.ProductionRequestNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PRPdfService {

    private final PRRepository prRepository;
    private final PRItemRepository prItemRepository;
    private final S3Uploader s3Uploader;

    @Transactional
    public String generateAndUpload(int prId) {
        ProductionRequest pr = prRepository.findById(prId)
                .orElseThrow(ProductionRequestNotFoundException::new);

        List<ProductionRequestItem> items =
                prItemRepository.findAllByProductionRequest_Id(prId);

        String html = buildHtml(pr, items);
        byte[] pdfBytes = PdfGenerator.generate(html);

        String fileName = pr.getPrCode() + ".pdf";
        return s3Uploader.uploadBytes(
                "sero/pr/",
                pdfBytes,
                fileName,
                "application/pdf"
        );
    }

    private String buildHtml(ProductionRequest pr, List<ProductionRequestItem> items) {
        DecimalFormat df = new DecimalFormat("#,###");
        int totalQuantity = items.stream().mapToInt(ProductionRequestItem::getQuantity).sum();

        StringBuilder sb = new StringBuilder();

        sb.append("<!DOCTYPE html PUBLIC \"-//XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">");
        sb.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
        sb.append("<head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"/>");

        sb.append("<style>");
        sb.append("@import url('https://fonts.googleapis.com/css2?family=Nanum+Gothic:wght@400;700&amp;display=swap');");

        sb.append("""
                body { font-family: 'Nanum Gothic', sans-serif; padding: 10mm; color: #333; }
                .header-container { text-align: center; margin-bottom: 30px; }
                .doc-title { font-size: 32px; letter-spacing: 15px; text-decoration: underline; margin-top: 20px; }
                
                table { width: 100%; border-collapse: collapse; margin-bottom: 15px; table-layout: fixed; }
                th, td { border: 1px solid #000; padding: 8px; font-size: 13px; word-break: break-all; }
                th { background: #f2f2f2; font-weight: bold; }
                
                .info-table th { width: 15%; }
                .info-table td { width: 35%; }
                
                .item-table th { text-align: center; }
                .center { text-align: center; }
                .right { text-align: right; }
                .bold { font-weight: bold; }
                .highlight-text { color: #d32f2f; font-weight: bold; }
                
                .footer-note { margin-top: 20px; font-size: 12px; border: 1px solid #000; padding: 10px; min-height: 60px; }
            </style>
            """);
        sb.append("</head><body>");

        // 제목
        sb.append("<div class=\"header-container\"><h1 class=\"doc-title\">생 산 요 청 서</h1></div>");

        // 데이터 바인딩 (Null-Safe)
        String soCode = (pr.getSalesOrder() != null) ? pr.getSalesOrder().getSoCode() : "-";
        String drafterName = (pr.getDrafter() != null) ? pr.getDrafter().getName() : "-";
        String managerName = (pr.getManager() != null) ? pr.getManager().getName() : "-";
        String createdAt = (pr.getCreatedAt() != null) ? pr.getCreatedAt().toString() : "-";

        // 상단 테이블
        sb.append("<table class=\"info-table\"><tbody>")
                .append("<tr><th>생산요청번호</th><td>").append(pr.getPrCode()).append("</td>")
                .append("<th>요청일자</th><td>").append(createdAt).append("</td></tr>")
                .append("<tr><th>주문번호</th><td>").append(soCode).append("</td>")
                .append("<th>납기일자</th><td class=\"highlight-text\">").append(pr.getDueAt()).append("</td></tr>")
                .append("<tr><th>요청자</th><td>").append(drafterName).append("</td>")
                .append("<th>담당자</th><td>").append(managerName).append("</td></tr>")
                .append("</tbody></table>");

        // 품목 테이블
        sb.append("<table class=\"item-table\">")
                .append("<thead><tr><th style=\"width:5%\">No</th><th style=\"width:15%\">품목코드</th><th style=\"width:30%\">품목명</th>")
                .append("<th style=\"width:20%\">규격</th><th style=\"width:10%\">단위</th><th style=\"width:20%\">수량</th></tr></thead><tbody>");

        int idx = 1;
        for (ProductionRequestItem item : items) {
            String itemCode = (item.getSalesOrderItem() != null) ? item.getSalesOrderItem().getItemCode() : "-";
            String itemName = (item.getSalesOrderItem() != null) ? item.getSalesOrderItem().getItemName() : "-";
            String spec = (item.getSalesOrderItem() != null && item.getSalesOrderItem().getSpec() != null) ? item.getSalesOrderItem().getSpec() : "-";
            String unit = (item.getSalesOrderItem() != null) ? item.getSalesOrderItem().getUnit() : "-";

            sb.append("<tr>")
                    .append("<td class=\"center\">").append(idx++).append("</td>")
                    .append("<td class=\"center\">").append(itemCode).append("</td>")
                    .append("<td>").append(itemName).append("</td>")
                    .append("<td class=\"center\">").append(spec).append("</td>")
                    .append("<td class=\"center\">").append(unit).append("</td>")
                    .append("<td class=\"right bold\">").append(df.format(item.getQuantity())).append("</td>")
                    .append("</tr>");
        }

        for (int n = items.size(); n < 10; n++) {
            sb.append("<tr><td class=\"center\">&#160;</td><td></td><td></td><td></td><td></td><td></td></tr>");
        }

        sb.append("</tbody><tfoot><tr><th colspan=\"5\">합 계</th>")
                .append("<td class=\"right bold\">").append(df.format(totalQuantity)).append("</td>")
                .append("</tr></tfoot></table>");

        sb.append("<div class=\"footer-note\"><p>※ 특이사항: 위와 같이 생산을 요청하오니 기한 내 납품 바랍니다.</p></div>");
        sb.append("</body></html>");

        return sb.toString();
    }
}