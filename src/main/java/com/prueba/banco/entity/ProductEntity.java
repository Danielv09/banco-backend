package com.prueba.banco.entity;

import com.prueba.banco.entity.enums.EstadoCuenta;
import com.prueba.banco.entity.enums.TipoCuenta;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "products")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El número de cuenta no puede estar vacío")
    @Column(name = "numero_cuenta", unique = true, nullable = false)
    private String numeroCuenta;

    @NotNull(message = "El tipo de cuenta es obligatorio")
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_cuenta", nullable = false)
    private TipoCuenta tipoCuenta;

    @NotNull(message = "El estado de la cuenta es obligatorio")
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoCuenta estado;

    @NotNull(message = "El saldo es obligatorio")
    @DecimalMin(value = "0.0", inclusive = true, message = "El saldo no puede ser negativo")
    @Column(name = "saldo", nullable = false)
    private BigDecimal saldo;

    @NotNull(message = "El saldo disponible es obligatorio")
    @DecimalMin(value = "0.0", inclusive = true, message = "El saldo disponible no puede ser negativo")
    @Column(name = "saldo_disponible", nullable = false)
    private BigDecimal saldoDisponible;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private ClientEntity cliente;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @Column(name = "fecha_modificacion")
    private LocalDateTime fechaModificacion;

    @PrePersist
    public void prePersist() {
        this.fechaCreacion = LocalDateTime.now();
        this.fechaModificacion = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.fechaModificacion = LocalDateTime.now();
    }

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

    public ClientEntity getCliente() { return cliente; }
    public void setCliente(ClientEntity cliente) { this.cliente = cliente; }

    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    public LocalDateTime getFechaModificacion() { return fechaModificacion; }
    public void setFechaModificacion(LocalDateTime fechaModificacion) { this.fechaModificacion = fechaModificacion; }
}