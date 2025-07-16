package com.alejandro.facturacion.repository;

import com.alejandro.facturacion.entity.InvoiceItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceItemRepository extends JpaRepository<InvoiceItem, Long> {
}

