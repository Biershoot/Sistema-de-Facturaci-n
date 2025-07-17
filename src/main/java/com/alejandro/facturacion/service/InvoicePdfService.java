package com.alejandro.facturacion.service;

import com.alejandro.facturacion.entity.Invoice;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;

@Service
public class InvoicePdfService {

    public ByteArrayInputStream generatePdf(Invoice invoice) {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            // Título
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Paragraph title = new Paragraph("Factura #" + invoice.getId(), titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph("Fecha: " + (invoice.getCreatedAt() != null ? invoice.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) : "")));
            document.add(new Paragraph("Cliente: " + (invoice.getClient() != null ? invoice.getClient().getName() : "")));
            document.add(new Paragraph(" "));

            // Tabla de productos
            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            table.addCell("Producto");
            table.addCell("Cantidad");
            table.addCell("Precio Unitario");
            table.addCell("Subtotal");

            if (invoice.getItems() != null) {
                invoice.getItems().forEach(item -> {
                    table.addCell(item.getProduct() != null ? item.getProduct().getName() : "");
                    table.addCell(String.valueOf(item.getQuantity()));
                    table.addCell("$" + (item.getProduct() != null ? item.getProduct().getPrice() : item.getPrice()));
                    BigDecimal subtotal = item.getSubtotal() != null ? item.getSubtotal() : (item.getProduct() != null && item.getProduct().getPrice() != null ? item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())) : BigDecimal.ZERO);
                    table.addCell("$" + subtotal);
                });
            }

            document.add(table);

            // Total
            document.add(new Paragraph(" "));
            Font totalFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14);
            Paragraph total = new Paragraph("Total: $" + (invoice.getTotal() != null ? invoice.getTotal() : ""), totalFont);
            total.setAlignment(Element.ALIGN_RIGHT);
            document.add(total);

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }
} 