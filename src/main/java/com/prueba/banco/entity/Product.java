package com.prueba.banco.entity;

import com.prueba.banco.enums.EstadoCuenta;
import com.prueba.banco.enums.TipoCuenta;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "products") // nombre en inglés para la tabla
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Tipo de cuenta: AHORROS o CORRIENTE
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_cuenta", nullable = false)
    @NotNull(message = "El tipo de cuenta es obligatorio")
    private TipoCuenta tipoCuenta;

    // Número de cuenta único, 10 dígitos
    @Column(name = "numero_cuenta", unique = true, length = 10, nullable = false)
    private String numeroCuenta;

    // Estado de la cuenta
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoCuenta estado;

    // Saldo de la cuenta
    @Column(name = "saldo", precision = 15, scale = 2, nullable = false)
    private BigDecimal saldo;

    // Exención de GMF
    @Column(name = "exenta_gmf", nullable = false)
    private Boolean exentaGMF;

    // Auditoría
    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_modificacion", nullable = false)
    private LocalDateTime fechaModificacion;

    // Relación con cliente
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Client cliente;

    // --- Ciclo de vida ---
    @PrePersist
    public void prePersist() {
        this.fechaCreacion = LocalDateTime.now();
        this.fechaModificacion = LocalDateTime.now();

        // Generar número de cuenta automáticamente
        if (this.numeroCuenta == null || this.numeroCuenta.isBlank()) {
            this.numeroCuenta = generarNumeroCuenta();
        }

        // Estado por defecto: ACTIVA si no se envía nada
        if (this.estado == null) {
            this.estado = EstadoCuenta.ACTIVA;
        }

        // Validar saldo en cuentas de ahorro
        if (this.tipoCuenta == TipoCuenta.AHORROS && this.saldo.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("La cuenta de ahorros no puede tener saldo menor a 0");
        }
    }

    @PreUpdate
    public void preUpdate() {
        this.fechaModificacion = LocalDateTime.now();

        // Validar cancelación solo si saldo = 0
        if (this.estado == EstadoCuenta.CANCELADA && this.saldo.compareTo(BigDecimal.ZERO) != 0) {
            throw new IllegalArgumentException("Solo se pueden cancelar cuentas con saldo igual a 0");
        }
    }

    private String generarNumeroCuenta() {
        String prefijo = (this.tipoCuenta == TipoCuenta.AHORROS) ? "53" : "33";
        String randomDigits = String.format("%08d", (long)(Math.random() * 100000000L));
        return prefijo + randomDigits;
    }

    // --- Getters y Setters completos ---
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

    public Client getCliente() { return cliente; }
    public void setCliente(Client cliente) { this.cliente = cliente; }
}