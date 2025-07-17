package com.alejandro.facturacion.utils;

import com.alejandro.facturacion.entity.Invoice;
import com.alejandro.facturacion.entity.InvoiceItem;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;

public class InvoicePdfExporter {

    public static ByteArrayInputStream exportInvoiceToPdf(Invoice invoice) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        PdfWriter writer = new PdfWriter(out);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        // Título
        Paragraph title = new Paragraph("Factura #" + invoice.getId())
                .setBold()
                .setFontSize(16);
        document.add(title);

        // Información de la factura
        document.add(new Paragraph("Fecha: " + (invoice.getCreatedAt() != null ? 
                invoice.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) : "")));
        
        if (invoice.getClient() != null) {
            document.add(new Paragraph("Cliente: " + invoice.getClient().getName()));
            document.add(new Paragraph("Email: " + invoice.getClient().getEmail()));
            document.add(new Paragraph("Identificación: " + invoice.getClient().getIdentificationNumber()));
        }
        
        document.add(new Paragraph(" "));

        // Tabla de productos
        Table table = new Table(4);
        table.addHeaderCell("Producto");
        table.addHeaderCell("Cantidad");
        table.addHeaderCell("Precio Unitario");
        table.addHeaderCell("Subtotal");

        if (invoice.getItems() != null) {
            for (InvoiceItem item : invoice.getItems()) {
                table.addCell(item.getProduct() != null ? item.getProduct().getName() : "");
                table.addCell(String.valueOf(item.getQuantity()));
                table.addCell("$" + (item.getProduct() != null ? item.getProduct().getPrice() : item.getPrice()));
                BigDecimal subtotal = item.getSubtotal() != null ? item.getSubtotal() : 
                    (item.getProduct() != null && item.getProduct().getPrice() != null ? 
                     item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())) : BigDecimal.ZERO);
                table.addCell("$" + subtotal);
            }
        }

        document.add(table);

        // Total
        document.add(new Paragraph(" "));
        if (invoice.getTotal() != null) {
            document.add(new Paragraph("Total: $" + invoice.getTotal()).setBold());
        }

        document.close();

        return new ByteArrayInputStream(out.toByteArray());
    }
} 