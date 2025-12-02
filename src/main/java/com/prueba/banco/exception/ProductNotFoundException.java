package com.prueba.banco.exception;


public class ProductNotFoundException extends RuntimeException {


    public ProductNotFoundException(Long id) {
        super("Producto con ID " + id + " no encontrado");
    }


    public ProductNotFoundException() {
        super("Producto no encontrado");
    }
}