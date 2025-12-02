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


    @PostMapping
    public ProductResponse create(@RequestBody ProductRequest req) {
        return service.create(req);
    }


    @GetMapping
    public List<ProductResponse> list() {
        return service.list();
    }


    @GetMapping("/{id}")
    public ProductResponse getById(@PathVariable Long id) {
        return service.getById(id);
    }


    @PutMapping("/{id}")
    public ProductResponse update(@PathVariable Long id, @RequestBody ProductRequest req) {
        return service.update(id, req);
    }


    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }


    @PatchMapping("/{id}/estado")
    public ProductResponse cambiarEstado(@PathVariable Long id, @RequestParam String nuevoEstado) {
        return service.cambiarEstado(id, nuevoEstado);
    }
}