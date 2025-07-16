package com.alejandro.facturacion.controller;

import com.alejandro.facturacion.dto.InvoiceItemRequest;
import com.alejandro.facturacion.entity.Invoice;
import com.alejandro.facturacion.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/invoices")
@RequiredArgsConstructor
public class InvoiceController {

    private final InvoiceService invoiceService;

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
}

