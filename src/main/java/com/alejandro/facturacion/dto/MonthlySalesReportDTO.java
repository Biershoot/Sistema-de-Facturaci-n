package com.alejandro.facturacion.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MonthlySalesReportDTO {
    private int year;
    private int month;
    private Long totalInvoices;
    private BigDecimal totalSales;
} 