package com.prueba.banco.dto;

import com.prueba.banco.entity.enums.TipoTransaccion;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public class TransactionRequest {

    @NotNull(message = "El tipo de transacción es obligatorio")
    private TipoTransaccion tipo;

    @NotNull(message = "El monto es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El monto debe ser mayor a cero")
    private BigDecimal monto;

    private Long productoOrigenId;
    private Long productoDestinoId;

    // Getters y setters
    public TipoTransaccion getTipo() { return tipo; }
    public void setTipo(TipoTransaccion tipo) { this.tipo = tipo; }

    public BigDecimal getMonto() { return monto; }
    public void setMonto(BigDecimal monto) { this.monto = monto; }

    public Long getProductoOrigenId() { return productoOrigenId; }
    public void setProductoOrigenId(Long productoOrigenId) { this.productoOrigenId = productoOrigenId; }

    public Long getProductoDestinoId() { return productoDestinoId; }
    public void setProductoDestinoId(Long productoDestinoId) { this.productoDestinoId = productoDestinoId; }
}