package com.prueba.banco.dto;

import com.prueba.banco.entity.enums.EstadoCuenta;
import com.prueba.banco.entity.enums.TipoCuenta;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ProductResponse {

    private Long id;
    private String numeroCuenta;
    private TipoCuenta tipoCuenta;
    private EstadoCuenta estado;
    private BigDecimal saldo;
    private BigDecimal saldoDisponible;
    private Long clienteId;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaModificacion;

    // Getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNumeroCuenta() { return numeroCuenta; }
    public void setNumeroCuenta(String numeroCuenta) { this.numeroCuenta = numeroCuenta; }

    public TipoCuenta getTipoCuenta() { return tipoCuenta; }
    public void setTipoCuenta(TipoCuenta tipoCuenta) { this.tipoCuenta = tipoCuenta; }

    public EstadoCuenta getEstado() { return estado; }
    public void setEstado(EstadoCuenta estado) { this.estado = estado; }

    public BigDecimal getSaldo() { return saldo; }
    public void setSaldo(BigDecimal saldo) { this.saldo = saldo; }

    public BigDecimal getSaldoDisponible() { return saldoDisponible; }
    public void setSaldoDisponible(BigDecimal saldoDisponible) { this.saldoDisponible = saldoDisponible; }

    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }

    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    public LocalDateTime getFechaModificacion() { return fechaModificacion; }
    public void setFechaModificacion(LocalDateTime fechaModificacion) { this.fechaModificacion = fechaModificacion; }
}