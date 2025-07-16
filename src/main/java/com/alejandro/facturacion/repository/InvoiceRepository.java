package com.alejandro.facturacion.repository;

import com.alejandro.facturacion.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    List<Invoice> findByClientId(Long clientId);

    List<Invoice> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
}

