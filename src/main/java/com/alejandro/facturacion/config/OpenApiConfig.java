package com.alejandro.facturacion.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Configuración personalizada para OpenAPI/Swagger.
 * 
 * <p>Esta configuración define la documentación de la API REST del sistema de facturación,
 * incluyendo información del proyecto, contacto del desarrollador y servidores disponibles.
 * 
 * @author Alejandro
 * @version 1.0
 * @since 2024
 */
@Configuration
public class OpenApiConfig {

    /**
     * Configura la información de la API para Swagger/OpenAPI.
     * 
     * @return Configuración de OpenAPI con metadatos del proyecto
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Sistema de Facturación API")
                        .description("""
                                API REST completa para gestión de facturación y ventas.
                                
                                ## Características Principales
                                - **Gestión de Clientes**: CRUD completo para clientes
                                - **Gestión de Productos**: Administración de productos con stock
                                - **Facturación**: Creación y gestión de facturas con múltiples productos
                                - **Generación de PDFs**: Exportación de facturas individuales y reportes
                                - **Reportes Mensuales**: Análisis de ventas por período
                                
                                ## Autenticación
                                La API utiliza autenticación básica HTTP:
                                - **Usuario**: admin
                                - **Contraseña**: admin123
                                
                                ## Tecnologías
                                - Spring Boot 3.2.4
                                - Spring Security
                                - Spring Data JPA
                                - MySQL
                                - iText 7 (PDF)
                                """)
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Alejandro")
                                .email("alejandro@example.com")
                                .url("https://github.com/Biershoot"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8081")
                                .description("Servidor de Desarrollo"),
                        new Server()
                                .url("https://api.facturacion.com")
                                .description("Servidor de Producción")
                ));
    }
} 