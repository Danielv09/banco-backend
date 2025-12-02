package com.prueba.banco.entity;

import com.prueba.banco.enums.TransactionType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transacciones")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_transaccion", nullable = false)
    @NotNull(message = "El tipo de transacción es obligatorio")
    private TransactionType tipo;


    @Column(name = "monto", precision = 15, scale = 2, nullable = false)
    @NotNull(message = "El monto es obligatorio")
    @Positive(message = "El monto debe ser mayor a 0")
    private BigDecimal monto;


    @Column(name = "fecha_transaccion", nullable = false)
    private LocalDateTime fechaTransaccion;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cuenta_id_origen")
    private Product cuentaOrigen;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cuenta_id_destino")
    private Product cuentaDestino;


    @Column(name = "saldo_disponible", precision = 15, scale = 2, nullable = false)
    private BigDecimal saldoDisponible;


    @PrePersist
    public void prePersist() {
        this.fechaTransaccion = LocalDateTime.now();
        if (this.saldoDisponible == null) {
            this.saldoDisponible = BigDecimal.ZERO;
        }
    }

    // Getters y Setters completos
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public TransactionType getTipo() { return tipo; }
    public void setTipo(TransactionType tipo) { this.tipo = tipo; }

    public BigDecimal getMonto() { return monto; }
    public void setMonto(BigDecimal monto) { this.monto = monto; }

    public LocalDateTime getFechaTransaccion() { return fechaTransaccion; }
    public void setFechaTransaccion(LocalDateTime fechaTransaccion) { this.fechaTransaccion = fechaTransaccion; }

    public Product getCuentaOrigen() { return cuentaOrigen; }
    public void setCuentaOrigen(Product cuentaOrigen) { this.cuentaOrigen = cuentaOrigen; }

    public Product getCuentaDestino() { return cuentaDestino; }
    public void setCuentaDestino(Product cuentaDestino) { this.cuentaDestino = cuentaDestino; }

    public BigDecimal getSaldoDisponible() { return saldoDisponible; }
    public void setSaldoDisponible(BigDecimal saldoDisponible) { this.saldoDisponible = saldoDisponible; }
}