package com.alejandro.facturacion.service;

import com.alejandro.facturacion.dto.MonthlySalesReportDTO;
import com.alejandro.facturacion.repository.InvoiceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService {

    private final InvoiceRepository invoiceRepository;

    public ReportService(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    public List<MonthlySalesReportDTO> getMonthlySales() {
        return invoiceRepository.getMonthlySalesReport();
    }
} 