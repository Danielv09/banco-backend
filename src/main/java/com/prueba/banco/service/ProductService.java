package com.prueba.banco.service;

import com.prueba.banco.dto.ProductRequest;
import com.prueba.banco.dto.ProductResponse;
import com.prueba.banco.entity.enums.EstadoCuenta;

import java.util.List;

public interface ProductService {
    ProductResponse crearProducto(ProductRequest request);
    ProductResponse obtenerProductoPorId(Long id);
    List<ProductResponse> listarProductos();
    ProductResponse actualizarProducto(Long id, ProductRequest request);
    ProductResponse actualizarEstado(Long id, EstadoCuenta estado);
    void eliminarProducto(Long id);
}