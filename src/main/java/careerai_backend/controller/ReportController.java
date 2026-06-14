package careerai_backend.controller;

import careerai_backend.service.PdfReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/report")
@CrossOrigin(origins = "http://localhost:5173")
public class ReportController {

    @Autowired
    private PdfReportService pdfReportService;

    @PostMapping("/download")
    public ResponseEntity<byte[]> downloadReport(
            @RequestBody String reportContent
    ) {

        byte[] pdf =
                pdfReportService.generateReport(
                        reportContent
                );

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=CareerAI_Report.pdf"
                )
                .contentType(
                        MediaType.APPLICATION_PDF
                )
                .body(pdf);
    }
}