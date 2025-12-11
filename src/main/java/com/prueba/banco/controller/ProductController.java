package com.prueba.banco.controller;

import com.prueba.banco.dto.ProductRequest;
import com.prueba.banco.dto.ProductResponse;
import com.prueba.banco.entity.enums.EstadoCuenta;
import com.prueba.banco.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.prueba.banco.dto.EstadoRequest;
import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // Crear producto
    @PostMapping
    public ResponseEntity<ProductResponse> crearProducto(@RequestBody ProductRequest request) {
        return ResponseEntity.status(201).body(productService.crearProducto(request));
    }

    // Obtener producto por ID
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> obtenerProductoPorId(@PathVariable Long id) {
        return ResponseEntity.ok(productService.obtenerProductoPorId(id));
    }

    // Listar todos los productos
    @GetMapping
    public ResponseEntity<List<ProductResponse>> listarProductos() {
        return ResponseEntity.ok(productService.listarProductos());
    }

    // Actualizar producto (tipoCuenta, saldo, cliente)
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> actualizarProducto(@PathVariable Long id,
                                                              @RequestBody ProductRequest request) {
        return ResponseEntity.ok(productService.actualizarProducto(id, request));
    }

    // Nuevo endpoint: actualizar estado de la cuenta
    @PutMapping("/{id}/estado")
    public ResponseEntity<ProductResponse> actualizarEstado(@PathVariable Long id,
                                                            @RequestBody EstadoRequest request) {
        return ResponseEntity.ok(productService.actualizarEstado(id, request.getEstado()));
    }

    // Eliminar producto
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
        productService.eliminarProducto(id);
        return ResponseEntity.noContent().build();
    }
}