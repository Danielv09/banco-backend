package com.prueba.banco.service;

import com.prueba.banco.dto.ProductRequest;
import com.prueba.banco.dto.ProductResponse;

import java.util.List;

public interface ProductService {
    ProductResponse create(ProductRequest req);
    List<ProductResponse> list();
    ProductResponse getById(Long id);
    ProductResponse update(Long id, ProductRequest req);
    void delete(Long id);
    ProductResponse cambiarEstado(Long id, String nuevoEstado);
}