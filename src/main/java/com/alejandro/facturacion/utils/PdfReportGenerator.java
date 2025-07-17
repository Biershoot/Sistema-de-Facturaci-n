package com.alejandro.facturacion.utils;

import com.alejandro.facturacion.dto.MonthlySalesReportDTO;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

public class PdfReportGenerator {

    public static ByteArrayInputStream generateMonthlySalesReport(List<MonthlySalesReportDTO> reportList) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        PdfWriter writer = new PdfWriter(out);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        // Título
        Paragraph title = new Paragraph("Reporte mensual de ventas")
                .setBold()
                .setFontSize(16);
        document.add(title);

        document.add(new Paragraph(" ")); // espacio

        // Tabla
        Table table = new Table(4);
        table.addHeaderCell("Año");
        table.addHeaderCell("Mes");
        table.addHeaderCell("Total Facturas");
        table.addHeaderCell("Ventas Totales");

        for (MonthlySalesReportDTO report : reportList) {
            table.addCell(String.valueOf(report.getYear()));
            table.addCell(String.valueOf(report.getMonth()));
            table.addCell(String.valueOf(report.getTotalInvoices()));
            table.addCell("$" + String.format("%,.2f", report.getTotalSales()));
        }

        document.add(table);
        document.close();

        return new ByteArrayInputStream(out.toByteArray());
    }
} 