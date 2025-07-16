package com.alejandro.facturacion.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "invoices")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime createdAt;

    private BigDecimal total;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Client client;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "invoice_id")
    @JsonIgnoreProperties({"invoice"})
    private List<InvoiceItem> items;

    @PrePersist
    public void setCreatedAt() {
        this.createdAt = LocalDateTime.now();
    }
}

