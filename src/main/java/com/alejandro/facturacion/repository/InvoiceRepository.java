package com.alejandro.facturacion.repository;

import com.alejandro.facturacion.dto.MonthlySalesReportDTO;
import com.alejandro.facturacion.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    List<Invoice> findByClientId(Long clientId);

    List<Invoice> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);

    @Query("SELECT new com.alejandro.facturacion.dto.MonthlySalesReportDTO(FUNCTION('YEAR', i.createdAt), FUNCTION('MONTH', i.createdAt), COUNT(i), SUM(i.total)) FROM Invoice i GROUP BY FUNCTION('YEAR', i.createdAt), FUNCTION('MONTH', i.createdAt) ORDER BY FUNCTION('YEAR', i.createdAt), FUNCTION('MONTH', i.createdAt)")
    List<MonthlySalesReportDTO> getMonthlySalesReport();
}

