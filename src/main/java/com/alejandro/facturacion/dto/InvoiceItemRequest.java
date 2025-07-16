package com.alejandro.facturacion.dto;

import lombok.Data;

@Data
public class InvoiceItemRequest {
    private Long productId;
    private Integer quantity;
}


