package com.alejandro.facturacion.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Entidad que representa un cliente en el sistema de facturación.
 * 
 * <p>Esta entidad maneja la información básica de los clientes incluyendo:
 * <ul>
 *   <li>Información de identificación personal</li>
 *   <li>Datos de contacto</li>
 *   <li>Fecha de registro</li>
 * </ul>
 * 
 * <p>La entidad utiliza anotaciones JPA para el mapeo objeto-relacional
 * y Lombok para reducir el código boilerplate.
 * 
 * @author Alejandro
 * @version 1.0
 * @since 2024
 */
@Entity
@Table(name = "clients")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Client {

    /**
     * Identificador único del cliente.
     * Se genera automáticamente usando estrategia de identidad.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nombre completo del cliente.
     * Campo obligatorio para identificación.
     */
    @Column(nullable = false)
    private String name;

    /**
     * Dirección de correo electrónico del cliente.
     * Debe ser única en el sistema.
     */
    @Column(unique = true, nullable = false)
    private String email;

    /**
     * Número de identificación del cliente (cédula, DNI, etc.).
     * Debe ser único en el sistema.
     */
    @Column(name = "identification_number", unique = true, nullable = false)
    private String identificationNumber;

    /**
     * Fecha y hora de creación del registro del cliente.
     * Se establece automáticamente antes de persistir.
     */
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    /**
     * Método que se ejecuta antes de persistir la entidad.
     * Establece automáticamente la fecha de creación.
     */
    @PrePersist
    public void setCreatedAt() {
        this.createdAt = LocalDateTime.now();
    }
}

