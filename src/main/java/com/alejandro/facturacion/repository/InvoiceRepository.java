package com.alejandro.facturacion.repository;

import com.alejandro.facturacion.dto.MonthlySalesReportDTO;
import com.alejandro.facturacion.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repositorio para el acceso a datos de la entidad Invoice.
 * 
 * <p>Este repositorio extiende JpaRepository para proporcionar operaciones CRUD básicas
 * y define consultas personalizadas para casos de uso específicos del negocio.
 * 
 * <p>Características principales:
 * <ul>
 *   <li>Operaciones CRUD estándar heredadas de JpaRepository</li>
 *   <li>Consultas personalizadas para búsquedas específicas</li>
 *   <li>Reportes agregados usando JPQL</li>
 *   <li>Consultas por rangos de fechas</li>
 * </ul>
 * 
 * @author Alejandro
 * @version 1.0
 * @since 2024
 */
@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    /**
     * Busca todas las facturas de un cliente específico.
     * 
     * @param clientId ID del cliente
     * @return Lista de facturas del cliente ordenadas por fecha de creación
     */
    List<Invoice> findByClientId(Long clientId);

    /**
     * Busca facturas creadas dentro de un rango de fechas.
     * 
     * @param start Fecha de inicio del rango (inclusive)
     * @param end Fecha de fin del rango (inclusive)
     * @return Lista de facturas en el rango especificado
     */
    List<Invoice> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);

    /**
     * Genera un reporte mensual de ventas agrupado por año y mes.
     * 
     * <p>Esta consulta utiliza funciones de base de datos para extraer año y mes
     * de la fecha de creación, y calcula agregaciones de conteo y suma.
     * 
     * @return Lista de reportes mensuales con año, mes, total de facturas y ventas totales
     */
    @Query("SELECT new com.alejandro.facturacion.dto.MonthlySalesReportDTO(FUNCTION('YEAR', i.createdAt), FUNCTION('MONTH', i.createdAt), COUNT(i), SUM(i.total)) FROM Invoice i GROUP BY FUNCTION('YEAR', i.createdAt), FUNCTION('MONTH', i.createdAt) ORDER BY FUNCTION('YEAR', i.createdAt), FUNCTION('MONTH', i.createdAt)")
    List<MonthlySalesReportDTO> getMonthlySalesReport();
}

