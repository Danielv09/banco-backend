package com.prueba.banco.dto;

import com.prueba.banco.enums.TransactionType;
import java.math.BigDecimal;

public class TransactionRequest {


    private TransactionType tipo;


    private BigDecimal monto;


    private Long cuentaOrigenId;


    private Long cuentaDestinoId;

    // Getters y Setters
    public TransactionType getTipo() { return tipo; }
    public void setTipo(TransactionType tipo) { this.tipo = tipo; }

    public BigDecimal getMonto() { return monto; }
    public void setMonto(BigDecimal monto) { this.monto = monto; }

    public Long getCuentaOrigenId() { return cuentaOrigenId; }
    public void setCuentaOrigenId(Long cuentaOrigenId) { this.cuentaOrigenId = cuentaOrigenId; }

    public Long getCuentaDestinoId() { return cuentaDestinoId; }
    public void setCuentaDestinoId(Long cuentaDestinoId) { this.cuentaDestinoId = cuentaDestinoId; }
}