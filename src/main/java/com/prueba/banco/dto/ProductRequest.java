package com.prueba.banco.dto;

import com.prueba.banco.entity.enums.TipoCuenta;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public class ProductRequest {

    @NotNull(message = "El tipo de cuenta es obligatorio")
    private TipoCuenta tipoCuenta;

    @NotNull(message = "El saldo inicial es obligatorio")
    @DecimalMin(value = "0.0", inclusive = true, message = "El saldo no puede ser negativo")
    private BigDecimal saldo;



    @NotNull(message = "El cliente es obligatorio")
    private Long clienteId;

    // Getters y setters
    public TipoCuenta getTipoCuenta() { return tipoCuenta; }
    public void setTipoCuenta(TipoCuenta tipoCuenta) { this.tipoCuenta = tipoCuenta; }

    public BigDecimal getSaldo() { return saldo; }
    public void setSaldo(BigDecimal saldo) { this.saldo = saldo; }



    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }
}