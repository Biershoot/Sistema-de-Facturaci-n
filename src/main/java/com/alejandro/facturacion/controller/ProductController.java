package com.alejandro.facturacion.controller;

import com.alejandro.facturacion.entity.Product;
import com.alejandro.facturacion.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para la gestión de productos.
 * Proporciona endpoints para crear, listar, buscar, obtener y eliminar productos.
 */
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Crea un nuevo producto.
     * @param product Producto a crear
     * @return Producto creado o error si ya existe el nombre
     */
    @PostMapping
    public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product) {
        if (productService.existsByName(product.getName())) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(productService.saveProduct(product));
    }

    /**
     * Obtiene la lista de todos los productos.
     * @return Lista de productos
     */
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    /**
     * Busca productos por nombre.
     * @param name Nombre a buscar
     * @return Lista de productos que coinciden con el nombre
     */
    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchByName(@RequestParam String name) {
        return ResponseEntity.ok(productService.searchByName(name));
    }

    /**
     * Obtiene un producto por su ID.
     * @param id ID del producto
     * @return Producto encontrado o 404 si no existe
     */
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return productService.getProductById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Elimina un producto por su ID.
     * @param id ID del producto
     * @return 204 No Content si se elimina correctamente
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}

