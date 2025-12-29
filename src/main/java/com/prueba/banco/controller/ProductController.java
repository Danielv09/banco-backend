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


    @PostMapping
    public ResponseEntity<ProductResponse> crearProducto(@RequestBody ProductRequest request) {
        return ResponseEntity.status(201).body(productService.crearProducto(request));
    }


    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> obtenerProductoPorId(@PathVariable Long id) {
        return ResponseEntity.ok(productService.obtenerProductoPorId(id));
    }


    @GetMapping
    public ResponseEntity<List<ProductResponse>> listarProductos() {
        return ResponseEntity.ok(productService.listarProductos());
    }


    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> actualizarProducto(@PathVariable Long id,
                                                              @RequestBody ProductRequest request) {
        return ResponseEntity.ok(productService.actualizarProducto(id, request));
    }


    @PutMapping("/{id}/estado")
    public ResponseEntity<ProductResponse> actualizarEstado(@PathVariable Long id,
                                                            @RequestBody EstadoRequest request) {
        return ResponseEntity.ok(productService.actualizarEstado(id, request.getEstado()));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarProducto(@PathVariable Long id) {
        productService.eliminarProducto(id);
        return ResponseEntity.ok("El producto ha sido eliminado exitosamente");
    }
}