package com.prueba.banco.entity;

import com.prueba.banco.entity.enums.TipoTransaccion;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "transactions")
public class TransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El tipo de transacción es obligatorio")
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private TipoTransaccion tipo;

    @NotNull(message = "El monto es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El monto debe ser mayor a cero")
    @Column(name = "monto", nullable = false)
    private BigDecimal monto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_origen_id")
    private ProductEntity productoOrigen;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_destino_id")
    private ProductEntity productoDestino;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @Column(name = "fecha")
    private LocalDateTime fecha;

    // ✅ Campos de auditoría de saldos
    @Column(name = "saldo_origen")
    private BigDecimal saldoOrigen;

    @Column(name = "saldo_disponible_origen")
    private BigDecimal saldoDisponibleOrigen;

    @Column(name = "saldo_destino")
    private BigDecimal saldoDestino;

    @Column(name = "saldo_disponible_destino")
    private BigDecimal saldoDisponibleDestino;

    @PrePersist
    public void prePersist() {
        this.fecha = LocalDateTime.now();
    }

    // Getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public TipoTransaccion getTipo() { return tipo; }
    public void setTipo(TipoTransaccion tipo) { this.tipo = tipo; }

    public BigDecimal getMonto() { return monto; }
    public void setMonto(BigDecimal monto) { this.monto = monto; }

    public ProductEntity getProductoOrigen() { return productoOrigen; }
    public void setProductoOrigen(ProductEntity productoOrigen) { this.productoOrigen = productoOrigen; }

    public ProductEntity getProductoDestino() { return productoDestino; }
    public void setProductoDestino(ProductEntity productoDestino) { this.productoDestino = productoDestino; }

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }

    public BigDecimal getSaldoOrigen() { return saldoOrigen; }
    public void setSaldoOrigen(BigDecimal saldoOrigen) { this.saldoOrigen = saldoOrigen; }

    public BigDecimal getSaldoDisponibleOrigen() { return saldoDisponibleOrigen; }
    public void setSaldoDisponibleOrigen(BigDecimal saldoDisponibleOrigen) { this.saldoDisponibleOrigen = saldoDisponibleOrigen; }

    public BigDecimal getSaldoDestino() { return saldoDestino; }
    public void setSaldoDestino(BigDecimal saldoDestino) { this.saldoDestino = saldoDestino; }

    public BigDecimal getSaldoDisponibleDestino() { return saldoDisponibleDestino; }
    public void setSaldoDisponibleDestino(BigDecimal saldoDisponibleDestino) { this.saldoDisponibleDestino = saldoDisponibleDestino; }
}