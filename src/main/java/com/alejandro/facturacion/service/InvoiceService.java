package com.alejandro.facturacion.service;

import com.alejandro.facturacion.dto.InvoiceItemRequest;
import com.alejandro.facturacion.entity.Invoice;
import com.alejandro.facturacion.entity.InvoiceItem;
import com.alejandro.facturacion.entity.Product;
import com.alejandro.facturacion.repository.ClientRepository;
import com.alejandro.facturacion.repository.InvoiceRepository;
import com.alejandro.facturacion.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Servicio que maneja la lógica de negocio relacionada con las facturas.
 * 
 * <p>Este servicio implementa las operaciones principales del sistema de facturación:
 * <ul>
 *   <li>Creación de facturas con validaciones de negocio</li>
 *   <li>Gestión de items de factura</li>
 *   <li>Cálculo automático de totales</li>
 *   <li>Validación de stock de productos</li>
 *   <li>Consulta de facturas por cliente</li>
 * </ul>
 * 
 * <p>Utiliza transacciones para garantizar la consistencia de datos
 * y maneja las reglas de negocio del sistema de facturación.
 * 
 * @author Alejandro
 * @version 1.0
 * @since 2024
 */
@Service
@RequiredArgsConstructor
@Transactional
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final ClientRepository clientRepository;
    private final ProductRepository productRepository;

    /**
     * Crea una nueva factura para un cliente con los productos especificados.
     * 
     * <p>Este método implementa las siguientes validaciones de negocio:
     * <ul>
     *   <li>Verifica que el cliente exista</li>
     *   <li>Valida que los productos existan</li>
     *   <li>Verifica disponibilidad de stock</li>
     *   <li>Calcula automáticamente los subtotales y total</li>
     * </ul>
     * 
     * @param clientId ID del cliente para el cual se crea la factura
     * @param items Lista de productos y cantidades a facturar
     * @return Factura creada con todos sus items y totales calculados
     * @throws IllegalArgumentException si el cliente no existe, productos no encontrados o stock insuficiente
     */
    public Invoice createInvoice(Long clientId, List<InvoiceItemRequest> items) {
        // Validar que el cliente existe
        var client = clientRepository.findById(clientId)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));

        // Crear la factura
        Invoice invoice = Invoice.builder()
                .client(client)
                .total(BigDecimal.ZERO)
                .build();

        // Procesar cada item de la factura
        for (InvoiceItemRequest itemRequest : items) {
            Product product = productRepository.findById(itemRequest.getProductId())
                    .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado: " + itemRequest.getProductId()));

            // Validar stock disponible
            if (product.getStock() < itemRequest.getQuantity()) {
                throw new IllegalArgumentException("Stock insuficiente para el producto: " + product.getName());
            }

            // Crear item de factura
            InvoiceItem invoiceItem = InvoiceItem.builder()
                    .product(product)
                    .quantity(itemRequest.getQuantity())
                    .price(product.getPrice())
                    .subtotal(product.getPrice().multiply(BigDecimal.valueOf(itemRequest.getQuantity())))
                    .build();

            // Actualizar stock del producto
            product.setStock(product.getStock() - itemRequest.getQuantity());
            productRepository.save(product);

            // Agregar item a la factura
            invoice.getItems().add(invoiceItem);
        }

        // Calcular total de la factura
        BigDecimal total = invoice.getItems().stream()
                .map(InvoiceItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        invoice.setTotal(total);

        return invoiceRepository.save(invoice);
    }

    /**
     * Obtiene todas las facturas del sistema.
     * 
     * @return Lista de todas las facturas ordenadas por fecha de creación
     */
    @Transactional(readOnly = true)
    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    /**
     * Busca una factura por su ID.
     * 
     * @param id ID de la factura a buscar
     * @return Optional con la factura si existe, vacío en caso contrario
     */
    @Transactional(readOnly = true)
    public Optional<Invoice> getInvoiceById(Long id) {
        return invoiceRepository.findById(id);
    }

    /**
     * Obtiene todas las facturas de un cliente específico.
     * 
     * @param clientId ID del cliente
     * @return Lista de facturas del cliente
     */
    @Transactional(readOnly = true)
    public List<Invoice> getInvoicesByClientId(Long clientId) {
        return invoiceRepository.findByClientId(clientId);
    }
}

