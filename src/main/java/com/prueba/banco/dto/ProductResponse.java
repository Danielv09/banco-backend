package com.prueba.banco.dto;

import com.prueba.banco.enums.EstadoCuenta;
import com.prueba.banco.enums.TipoCuenta;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ProductResponse {
    private Long id;
    private TipoCuenta tipoCuenta;
    private String numeroCuenta;
    private EstadoCuenta estado;
    private BigDecimal saldo;
    private Boolean exentaGMF;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaModificacion;
    private Long clienteId;

    // Getters y Setters completos
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public TipoCuenta getTipoCuenta() { return tipoCuenta; }
    public void setTipoCuenta(TipoCuenta tipoCuenta) { this.tipoCuenta = tipoCuenta; }

    public String getNumeroCuenta() { return numeroCuenta; }
    public void setNumeroCuenta(String numeroCuenta) { this.numeroCuenta = numeroCuenta; }

    public EstadoCuenta getEstado() { return estado; }
    public void setEstado(EstadoCuenta estado) { this.estado = estado; }

    public BigDecimal getSaldo() { return saldo; }
    public void setSaldo(BigDecimal saldo) { this.saldo = saldo; }

    public Boolean getExentaGMF() { return exentaGMF; }
    public void setExentaGMF(Boolean exentaGMF) { this.exentaGMF = exentaGMF; }

    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    public LocalDateTime getFechaModificacion() { return fechaModificacion; }
    public void setFechaModificacion(LocalDateTime fechaModificacion) { this.fechaModificacion = fechaModificacion; }

    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }
}