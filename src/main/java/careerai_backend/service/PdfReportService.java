package careerai_backend.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
public class PdfReportService {

    public byte[] generateReport(
            String reportContent
    ) {

        try {

            ByteArrayOutputStream outputStream =
                    new ByteArrayOutputStream();

            Document document =
                    new Document();

            PdfWriter.getInstance(
                    document,
                    outputStream
            );

            document.open();

            Font titleFont =
                    FontFactory.getFont(
                            FontFactory.HELVETICA_BOLD,
                            18
                    );

            Font contentFont =
                    FontFactory.getFont(
                            FontFactory.HELVETICA,
                            12
                    );

            document.add(
                    new Paragraph(
                            "CareerAI Report",
                            titleFont
                    )
            );

            document.add(
                    new Paragraph(" ")
            );

            document.add(
                    new Paragraph(
                            reportContent,
                            contentFont
                    )
            );

            document.close();

            return outputStream.toByteArray();

        } catch (Exception e) {

            e.printStackTrace();

            return null;
        }
    }
}