package com.alejandro.facturacion.service;

import com.alejandro.facturacion.entity.*;
import com.alejandro.facturacion.repository.ClientRepository;
import com.alejandro.facturacion.repository.InvoiceRepository;
import com.alejandro.facturacion.repository.ProductRepository;
import com.alejandro.facturacion.dto.InvoiceItemRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final ProductRepository productRepository;
    private final ClientRepository clientRepository;

    public Optional<Invoice> getInvoiceById(Long id) {
        return invoiceRepository.findById(id);
    }

    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    public List<Invoice> getInvoicesByClientId(Long clientId) {
        return invoiceRepository.findByClientId(clientId);
    }

    public Invoice createInvoice(Long clientId, List<InvoiceItemRequest> items) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));

        List<InvoiceItem> invoiceItems = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (InvoiceItemRequest itemReq : items) {
            Product product = productRepository.findById(itemReq.getProductId())
                    .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado: " + itemReq.getProductId()));

            if (product.getStock() < itemReq.getQuantity()) {
                throw new IllegalArgumentException("Stock insuficiente para: " + product.getName());
            }

            // Descontar stock
            product.setStock(product.getStock() - itemReq.getQuantity());
            productRepository.save(product);

            BigDecimal subtotal = product.getPrice().multiply(BigDecimal.valueOf(itemReq.getQuantity()));

            InvoiceItem item = InvoiceItem.builder()
                    .product(product)
                    .quantity(itemReq.getQuantity())
                    .price(product.getPrice())
                    .subtotal(subtotal)
                    .build();

            invoiceItems.add(item);
            total = total.add(subtotal);
        }

        Invoice invoice = Invoice.builder()
                .client(client)
                .items(invoiceItems)
                .total(total)
                .build();

        return invoiceRepository.save(invoice);
    }
}

