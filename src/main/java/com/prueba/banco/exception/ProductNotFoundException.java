package com.prueba.banco.exception;

// Se lanza cuando no existe un producto (cuenta) con el ID solicitado
public class ProductNotFoundException extends RuntimeException {

    // Constructor con ID: mensaje específico para depurar más fácil
    public ProductNotFoundException(Long id) {
        super("Producto con ID " + id + " no encontrado");
    }

    // Constructor genérico opcional: útil si no tienes el ID disponible
    public ProductNotFoundException() {
        super("Producto no encontrado");
    }
}