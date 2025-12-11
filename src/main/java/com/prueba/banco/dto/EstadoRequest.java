package com.prueba.banco.dto;

import com.prueba.banco.entity.enums.EstadoCuenta;

public class EstadoRequest {
    private EstadoCuenta estado;

    public EstadoCuenta getEstado() {
        return estado;
    }

    public void setEstado(EstadoCuenta estado) {
        this.estado = estado;
    }
}