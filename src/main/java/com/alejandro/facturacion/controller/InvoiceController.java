package com.alejandro.facturacion.controller;

import com.alejandro.facturacion.dto.InvoiceItemRequest;
import com.alejandro.facturacion.entity.Invoice;
import com.alejandro.facturacion.service.InvoiceService;
import com.alejandro.facturacion.service.InvoicePdfService;
import com.alejandro.facturacion.utils.InvoicePdfExporter;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.ByteArrayInputStream;
import java.util.List;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

    private final InvoiceService invoiceService;
    private final InvoicePdfService pdfService;

    public InvoiceController(InvoiceService invoiceService, InvoicePdfService pdfService) {
        this.invoiceService = invoiceService;
        this.pdfService = pdfService;
    }

    // Crear factura
    @PostMapping("/{clientId}")
    public ResponseEntity<Invoice> createInvoice(
            @PathVariable Long clientId,
            @RequestBody List<InvoiceItemRequest> items) {

        try {
            Invoice invoice = invoiceService.createInvoice(clientId, items);
            return ResponseEntity.ok(invoice);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // Obtener todas las facturas
    @GetMapping
    public ResponseEntity<List<Invoice>> getAllInvoices() {
        return ResponseEntity.ok(invoiceService.getAllInvoices());
    }

    // Obtener factura por ID
    @GetMapping("/{id}")
    public ResponseEntity<Invoice> getInvoiceById(@PathVariable Long id) {
        return invoiceService.getInvoiceById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Obtener facturas por cliente
    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<Invoice>> getInvoicesByClientId(@PathVariable Long clientId) {
        return ResponseEntity.ok(invoiceService.getInvoicesByClientId(clientId));
    }

    // Descargar PDF de la factura
    @GetMapping("/{invoiceId}/pdf")
    public ResponseEntity<InputStreamResource> generateInvoicePdf(@PathVariable Long invoiceId) {
        Invoice invoice = invoiceService.getInvoiceById(invoiceId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Factura no encontrada"));

        ByteArrayInputStream pdfStream = pdfService.generatePdf(invoice);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=factura_" + invoiceId + ".pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(pdfStream));
    }

    // Exportar factura como PDF (nuevo endpoint)
    @GetMapping("/{id}/export")
    public ResponseEntity<byte[]> exportInvoiceAsPdf(@PathVariable Long id) {
        Invoice invoice = invoiceService.getInvoiceById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Factura no encontrada"));

        ByteArrayInputStream pdfStream = InvoicePdfExporter.exportInvoiceToPdf(invoice);
        byte[] pdfBytes = new byte[0];
        try {
            pdfBytes = pdfStream.readAllBytes();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al generar PDF");
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=factura_" + id + ".pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }
}

