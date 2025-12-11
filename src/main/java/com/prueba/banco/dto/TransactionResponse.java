package com.prueba.banco.dto;

import com.prueba.banco.entity.enums.TipoTransaccion;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionResponse {

    private Long id;
    private TipoTransaccion tipo;
    private BigDecimal monto;
    private Long productoOrigenId;
    private Long productoDestinoId;

    // Nuevos campos para mostrar saldos actualizados
    private BigDecimal saldoOrigen;
    private BigDecimal saldoDisponibleOrigen;
    private BigDecimal saldoDestino;
    private BigDecimal saldoDisponibleDestino;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime fecha;

    // Getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public TipoTransaccion getTipo() { return tipo; }
    public void setTipo(TipoTransaccion tipo) { this.tipo = tipo; }

    public BigDecimal getMonto() { return monto; }
    public void setMonto(BigDecimal monto) { this.monto = monto; }

    public Long getProductoOrigenId() { return productoOrigenId; }
    public void setProductoOrigenId(Long productoOrigenId) { this.productoOrigenId = productoOrigenId; }

    public Long getProductoDestinoId() { return productoDestinoId; }
    public void setProductoDestinoId(Long productoDestinoId) { this.productoDestinoId = productoDestinoId; }

    public BigDecimal getSaldoOrigen() { return saldoOrigen; }
    public void setSaldoOrigen(BigDecimal saldoOrigen) { this.saldoOrigen = saldoOrigen; }

    public BigDecimal getSaldoDisponibleOrigen() { return saldoDisponibleOrigen; }
    public void setSaldoDisponibleOrigen(BigDecimal saldoDisponibleOrigen) { this.saldoDisponibleOrigen = saldoDisponibleOrigen; }

    public BigDecimal getSaldoDestino() { return saldoDestino; }
    public void setSaldoDestino(BigDecimal saldoDestino) { this.saldoDestino = saldoDestino; }

    public BigDecimal getSaldoDisponibleDestino() { return saldoDisponibleDestino; }
    public void setSaldoDisponibleDestino(BigDecimal saldoDisponibleDestino) { this.saldoDisponibleDestino = saldoDisponibleDestino; }

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
}