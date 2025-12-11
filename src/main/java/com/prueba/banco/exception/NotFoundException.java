package com.prueba.banco.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) { super(message); }
}