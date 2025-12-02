package com.prueba.banco.controller;

import com.prueba.banco.dto.ProductRequest;
import com.prueba.banco.dto.ProductResponse;
import com.prueba.banco.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    // Create product (account)
    @PostMapping
    public ProductResponse create(@RequestBody ProductRequest req) {
        return service.create(req);
    }

    // List all products
    @GetMapping
    public List<ProductResponse> list() {
        return service.list();
    }

    // Get product by ID
    @GetMapping("/{id}")
    public ProductResponse getById(@PathVariable Long id) {
        return service.getById(id);
    }

    // Update product
    @PutMapping("/{id}")
    public ProductResponse update(@PathVariable Long id, @RequestBody ProductRequest req) {
        return service.update(id, req);
    }

    // Delete product
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    // Change account status (ACTIVO, INACTIVO, CANCELADO, BLOQUEADO)
    @PatchMapping("/{id}/estado")
    public ProductResponse cambiarEstado(@PathVariable Long id, @RequestParam String nuevoEstado) {
        return service.cambiarEstado(id, nuevoEstado);
    }
}