package com.alejandro.facturacion.controller;

import com.alejandro.facturacion.dto.MonthlySalesReportDTO;
import com.alejandro.facturacion.service.ReportService;
import com.alejandro.facturacion.utils.PdfReportGenerator;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/monthly-sales")
    public ResponseEntity<List<MonthlySalesReportDTO>> getMonthlySalesReport() {
        List<MonthlySalesReportDTO> report = reportService.getMonthlySales();
        return ResponseEntity.ok(report);
    }

    @GetMapping("/monthly-sales/pdf")
    public ResponseEntity<InputStreamResource> exportMonthlySalesPdf() {
        List<MonthlySalesReportDTO> reportList = reportService.getMonthlySales();

        ByteArrayInputStream bis = PdfReportGenerator.generateMonthlySalesReport(reportList);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=reporte_mensual_ventas.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }
} 