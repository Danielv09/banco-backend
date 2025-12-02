package com.prueba.banco.dto;

import com.prueba.banco.enums.TipoCuenta;
import java.math.BigDecimal;

public class ProductRequest {
    private TipoCuenta tipoCuenta;
    private BigDecimal saldo;
    private Boolean exentaGMF;
    private Long clienteId;

    // --- Getters y Setters completos ---
    public TipoCuenta getTipoCuenta() { return tipoCuenta; }
    public void setTipoCuenta(TipoCuenta tipoCuenta) { this.tipoCuenta = tipoCuenta; }

    public BigDecimal getSaldo() { return saldo; }
    public void setSaldo(BigDecimal saldo) { this.saldo = saldo; }

    public Boolean getExentaGMF() { return exentaGMF; }
    public void setExentaGMF(Boolean exentaGMF) { this.exentaGMF = exentaGMF; }

    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }
}