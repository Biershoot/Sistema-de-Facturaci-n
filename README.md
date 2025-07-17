# 🧾 Sistema de Facturación y Gestión de Ventas

Un sistema completo de facturación desarrollado con Spring Boot que permite gestionar clientes, productos, facturas y generar reportes en PDF.

## ✨ Características Principales

- **Gestión de Clientes**: CRUD completo para clientes
- **Gestión de Productos**: Administración de productos con stock
- **Facturación**: Creación y gestión de facturas con múltiples productos
- **Generación de PDFs**: Exportación de facturas individuales y reportes
- **Reportes Mensuales**: Análisis de ventas por período
- **API REST**: Endpoints completos para integración
- **Autenticación**: Seguridad básica implementada
- **Base de Datos**: MySQL con JPA/Hibernate

## 🛠️ Tecnologías Utilizadas

- **Backend**: Spring Boot 3.2.4
- **Base de Datos**: MySQL 8.0
- **ORM**: Spring Data JPA / Hibernate
- **Seguridad**: Spring Security
- **PDF**: iText 7 + LibrePDF
- **Documentación**: Swagger/OpenAPI
- **Build Tool**: Maven
- **Java**: JDK 17

## 🚀 Instalación y Configuración

### Prerrequisitos

- Java 17 o superior
- Maven 3.6+
- MySQL 8.0+

### Configuración de Base de Datos

1. Crea una base de datos MySQL:
```sql
CREATE DATABASE facturacion;
```

2. Configura las credenciales en `src/main/resources/application.properties`:
```properties
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña
```

### Ejecución

1. Clona el repositorio:
```bash
git clone https://github.com/tu-usuario/facturacion.git
cd facturacion
```

2. Compila el proyecto:
```bash
mvn clean install
```

3. Ejecuta la aplicación:
```bash
mvn spring-boot:run
```

La aplicación estará disponible en: `http://localhost:8081`

## 📚 API Endpoints

### Autenticación
- **Usuario por defecto**: `admin`
- **Contraseña por defecto**: `admin123`

### Clientes
- `GET /api/clients` - Obtener todos los clientes
- `GET /api/clients/{id}` - Obtener cliente por ID
- `POST /api/clients` - Crear nuevo cliente
- `PUT /api/clients/{id}` - Actualizar cliente
- `DELETE /api/clients/{id}` - Eliminar cliente

### Productos
- `GET /api/products` - Obtener todos los productos
- `GET /api/products/{id}` - Obtener producto por ID
- `POST /api/products` - Crear nuevo producto
- `PUT /api/products/{id}` - Actualizar producto
- `DELETE /api/products/{id}` - Eliminar producto

### Facturas
- `GET /api/invoices` - Obtener todas las facturas
- `GET /api/invoices/{id}` - Obtener factura por ID
- `POST /api/invoices/{clientId}` - Crear nueva factura
- `GET /api/invoices/{id}/pdf` - Descargar PDF de factura
- `GET /api/invoices/{id}/export` - Exportar factura como PDF

### Reportes
- `GET /api/reports/monthly-sales` - Reporte mensual de ventas (JSON)
- `GET /api/reports/monthly-sales/pdf` - Reporte mensual de ventas (PDF)

## 📋 Estructura del Proyecto

```
src/main/java/com/alejandro/facturacion/
├── config/                 # Configuraciones
│   ├── SecurityConfig.java
│   └── DatabaseCreator.java
├── controller/            # Controladores REST
│   ├── ClientController.java
│   ├── ProductController.java
│   ├── InvoiceController.java
│   └── ReportController.java
├── entity/               # Entidades JPA
│   ├── Client.java
│   ├── Product.java
│   ├── Invoice.java
│   └── InvoiceItem.java
├── repository/           # Repositorios de datos
│   ├── ClientRepository.java
│   ├── ProductRepository.java
│   ├── InvoiceRepository.java
│   └── InvoiceItemRepository.java
├── service/             # Lógica de negocio
│   ├── ClientService.java
│   ├── ProductService.java
│   ├── InvoiceService.java
│   ├── InvoicePdfService.java
│   └── ReportService.java
├── dto/                 # Objetos de transferencia
│   ├── InvoiceItemRequest.java
│   └── MonthlySalesReportDTO.java
└── utils/               # Utilidades
    ├── InvoicePdfExporter.java
    └── PdfReportGenerator.java
```

## 🔐 Seguridad

El sistema utiliza autenticación básica HTTP. Para cambiar las credenciales por defecto, modifica:

```properties
spring.security.user.name=tu_usuario
spring.security.user.password=tu_contraseña
```

## 📊 Generación de PDFs

### Facturas Individuales
- Endpoint: `GET /api/invoices/{id}/export`
- Incluye: Información del cliente, productos, cantidades y totales
- Formato: PDF profesional con tabla de productos

### Reportes Mensuales
- Endpoint: `GET /api/reports/monthly-sales/pdf`
- Incluye: Resumen de ventas por año y mes
- Formato: Tabla con totales de facturas y ventas

## 🧪 Pruebas

### Con Postman

1. **Configuración de Autenticación**:
   - Tipo: Basic Auth
   - Username: `admin`
   - Password: `admin123`

2. **Ejemplos de Peticiones**:

```bash
# Obtener todas las facturas
GET http://localhost:8081/api/invoices
Authorization: Basic YWRtaW46YWRtaW4xMjM=

# Exportar factura como PDF
GET http://localhost:8081/api/invoices/1/export
Authorization: Basic YWRtaW46YWRtaW4xMjM=

# Obtener reporte mensual
GET http://localhost:8081/api/reports/monthly-sales
Authorization: Basic YWRtaW46YWRtaW4xMjM=
```

## 📖 Documentación API

Una vez ejecutada la aplicación, accede a la documentación Swagger en:
- **Swagger UI**: `http://localhost:8081/swagger-ui.html`
- **OpenAPI JSON**: `http://localhost:8081/api-docs`

## 🔧 Configuración Adicional

### Correo Electrónico (Opcional)
Para habilitar el envío de facturas por email, configura en `application.properties`:

```properties
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=tu_correo@gmail.com
spring.mail.password=tu_app_password
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```

### Puerto de la Aplicación
Cambia el puerto por defecto en `application.properties`:
```properties
server.port=8080
```

## 🤝 Contribuciones

1. Fork el proyecto
2. Crea una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

## 📝 Licencia

Este proyecto está bajo la Licencia MIT. Ver el archivo `LICENSE` para más detalles.

## 👨‍💻 Autor

**Alejandro** - [GitHub](https://github.com/tu-usuario)

## 🙏 Agradecimientos

- Spring Boot Team
- iText Software
- MySQL Community
- La comunidad de desarrolladores Java

---

⭐ Si este proyecto te ha sido útil, ¡dale una estrella en GitHub! 