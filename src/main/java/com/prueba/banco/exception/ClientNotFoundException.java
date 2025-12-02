package com.prueba.banco.exception;

public class ClientNotFoundException extends RuntimeException {
    public ClientNotFoundException(Long id) {
        super("Cliente con ID " + id + " no encontrado");
    }
}